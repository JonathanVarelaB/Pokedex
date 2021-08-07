package com.jvarela.pokdex.fragment

import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.jakewharton.rxbinding4.widget.textChanges
import com.jvarela.pokdex.R
import com.jvarela.pokdex.databinding.LoginFragmentBinding
import com.jvarela.pokdex.db.entity.User
import com.jvarela.pokdex.viewModel.DBViewModel
import java.util.concurrent.TimeUnit
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable

class LoginFragment: Fragment(R.layout.login_fragment){

    private var _binding: LoginFragmentBinding? = null
    private val binding get() = _binding!!
    private var sex: Sex? = null
    private val viewModel: DBViewModel by activityViewModels()
    private val disposable = CompositeDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.userNameEditText.addTextChangedListener { validForm() }
        binding.userEmailEditText.addTextChangedListener { validForm() }
        binding.maleRadButton.setOnClickListener{
            sex = Sex.MALE
            validForm()
        }
        binding.femaleRadButton.setOnClickListener{
            sex = Sex.FEMALE
            validForm()
        }
        binding.loginButton.setOnClickListener { login() }
        validForm()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LoginFragmentBinding.inflate(inflater, container, false)
        validSessionActive()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun validSessionActive(){
        var user: User? = viewModel.getUserSession()
        if(user != null) {
            findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToMainListFragment(MainNavAccess.LOGIN)
            )
        }
        else{
            disposable.add(
                binding.userEmailEditText.textChanges()
                    .debounce(800, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        binding.userEmailEditText.error = null
                        if(it.toString().isNotEmpty() && !isValidEmail(it.toString()))
                            binding.userEmailEditText.error = "Dato no válido"
                    }
            )
        }
    }

    fun validForm(){
        binding.loginButton.isEnabled = true
        if (binding.userNameEditText.text.toString().isEmpty() || binding.userEmailEditText.text.toString().isEmpty() || sex == null){
            binding.loginButton.isEnabled = false
        }
    }

    fun login(){
        binding.loader.visibility = View.VISIBLE
        var navAccess: MainNavAccess = MainNavAccess.LOGIN
        viewModel.existUser(binding.userEmailEditText.text.toString()).observe(viewLifecycleOwner) {
            if(it.count() > 0)
                viewModel.updateUser(binding.userEmailEditText.text.toString(), binding.userNameEditText.text.toString(), sex)
            else{
                viewModel.registerUser(binding.userEmailEditText.text.toString(), binding.userNameEditText.text.toString(), sex)
                navAccess = MainNavAccess.REGISTER
            }
        }
        Handler().postDelayed({
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToMainListFragment(navAccess))
        }, 1000)
    }

    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    override fun onDestroy() {
        disposable.clear()
        super.onDestroy()
    }

}

enum class Sex(val id: String, val letter: String) {
    MALE("0", "o"),
    FEMALE("1", "a")
}

enum class MainNavAccess{
    REGISTER, LOGIN, BACK
}