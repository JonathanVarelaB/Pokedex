package com.jvarela.pokdex.fragment

import android.content.ClipData
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Filter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jvarela.pokdex.R
import com.jvarela.pokdex.model.FilterList
import com.jvarela.pokdex.model.ListViewModel

class MainListFragment: Fragment(R.layout.main_list_fragment) {

    private lateinit var bottomNav: BottomNavigationView
    private lateinit var logoutButton: Button
    private val viewModel: ListViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottomNav = view.findViewById(R.id.bottomNavigationView)
        logoutButton = view.findViewById(R.id.logOutButton)

        viewModel.originalList.observe(viewLifecycleOwner, Observer { list ->
            if(list != null){
                if(viewModel.selected == null)
                    onFilterSelected(FilterList.FAVORITES)
                else{
                    if(viewModel.selected?.value == FilterList.ALL){
                        bottomNav.selectedItemId = R.id.allOption
                    }
                    else if(viewModel.selected?.value == FilterList.FAVORITES) {
                        bottomNav.selectedItemId = R.id.favoriteButton
                    }
                    else if(viewModel.selected?.value == FilterList.VIEWED) {
                        bottomNav.selectedItemId = R.id.viewedOption
                    }
                }
            }
        })

        bottomNav.setOnNavigationItemSelectedListener { item ->
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

        logoutButton.setOnClickListener{
            findNavController().navigate(MainListFragmentDirections.actionMainListFragmentToLoginFragment())
        }
    }

    fun onFilterSelected(filter: FilterList) = viewModel.select(filter)

}