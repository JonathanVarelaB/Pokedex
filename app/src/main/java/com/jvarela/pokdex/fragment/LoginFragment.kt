package com.jvarela.pokdex.fragment

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.textfield.TextInputEditText
import com.jvarela.pokdex.R
import com.jvarela.pokdex.manager.LoaderManager

class LoginFragment: Fragment(R.layout.login_fragment){

    private lateinit var txtUserName: TextInputEditText
    private lateinit var txtUserEmail: TextInputEditText
    private lateinit var btnLogin: Button
    private lateinit var rbtnMale: RadioButton
    private lateinit var rbtnFemale: RadioButton
    private var sex: Sex? = null
    private lateinit var loader: CircularProgressIndicator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txtUserName = view.findViewById(R.id.userNameEditText)
        txtUserEmail = view.findViewById(R.id.userEmailEditText)
        btnLogin = view.findViewById(R.id.loginButton)
        rbtnMale = view.findViewById(R.id.maleRadButton)
        rbtnFemale = view.findViewById(R.id.femaleRadButton)
        loader = view.findViewById(R.id.loader)

        txtUserName.addTextChangedListener { validForm() }
        txtUserEmail.addTextChangedListener { validForm() }
        rbtnMale.setOnClickListener{
            sex = Sex.MALE
            validForm()
        }
        rbtnFemale.setOnClickListener{
            sex = Sex.FEMALE
            validForm()
        }
        btnLogin.setOnClickListener { login() }
        validForm()
    }

    fun validForm(){
        btnLogin.isEnabled = true
        if (txtUserName.text.toString().isEmpty() || txtUserEmail.text.toString().isEmpty() || sex == null){
            btnLogin.isEnabled = false
        }
    }

    fun login(){
        LoaderManager().showLoader(loader, btnLogin)
        Handler().postDelayed({
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToMainListFragment())
            LoaderManager().hideLoader(loader, btnLogin)
        }, 1000)
    }

}

enum class Sex {
    MALE, FEMALE
}