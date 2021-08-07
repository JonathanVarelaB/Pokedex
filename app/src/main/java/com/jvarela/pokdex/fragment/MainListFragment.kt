package com.jvarela.pokdex.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jvarela.pokdex.R
import com.jvarela.pokdex.databinding.MainListFragmentBinding
import com.jvarela.pokdex.db.entity.User
import com.jvarela.pokdex.model.APIViewModel
import com.jvarela.pokdex.model.FilterList

class MainListFragment: Fragment(R.layout.main_list_fragment) {

    private var _binding: MainListFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: APIViewModel by activityViewModels()
    private val args: MainListFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showGreeting()
        viewModel.originalList.observe(viewLifecycleOwner, Observer { list ->
            if(list != null){
                if(viewModel.selected == null)
                    onFilterSelected(FilterList.ALL)
                else{
                    if(viewModel.selected?.value == FilterList.ALL){
                        binding.bottomNavigationView.selectedItemId = R.id.allOption
                    }
                    else if(viewModel.selected?.value == FilterList.FAVORITES) {
                        binding.bottomNavigationView.selectedItemId = R.id.favoriteButton
                    }
                    else if(viewModel.selected?.value == FilterList.VIEWED) {
                        binding.bottomNavigationView.selectedItemId = R.id.viewedOption
                    }
                }
            }
        })

        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.favoriteOption -> {
                    onFilterSelected(FilterList.FAVORITES)
                    true
                }
                R.id.allOption -> {
                    onFilterSelected(FilterList.ALL)
                    true
                }
                R.id.viewedOption -> {
                    onFilterSelected(FilterList.VIEWED)
                    true
                }
                else -> false
            }
        }

        binding.logOutButton.setOnClickListener{
            viewModel.logout()
            findNavController().navigate(MainListFragmentDirections.actionMainListFragmentToLoginFragment())
        }
    }

    fun onFilterSelected(filter: FilterList) = viewModel.select(filter)

    fun showGreeting(){
        if (args.navAccess != MainNavAccess.BACK) {
            var user: User? = viewModel.getUserSession()
            if (user != null) {
                val name: String? = user?.name
                val letter: String = user?.sex?.letter ?: ""
                var greeting: String = "Bienvenid$letter $name"
                if (args.navAccess == MainNavAccess.REGISTER)
                    greeting = "Registro exitoso. $greeting"
                Toast.makeText(context, greeting, Toast.LENGTH_LONG).show()
            }
        }
    }

}