package com.jvarela.pokdex.fragment

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.jvarela.pokdex.R
import com.jvarela.pokdex.adapter.PokemonListAdapter
import com.jvarela.pokdex.manager.LoaderManager
import com.jvarela.pokdex.model.ListViewModel
import com.jvarela.pokdex.model.Pokemon
import com.jvarela.pokdex.model.Stat

class ListFragment:Fragment(R.layout.list_fragment) {

    private val viewModel: ListViewModel by activityViewModels()
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var emptyListTextView: TextView
    private lateinit var loader: CircularProgressIndicator

    private val adapter = PokemonListAdapter(
        { pokemon -> findNavController().navigate(MainListFragmentDirections.actionMainListFragmentToDetailFragment(pokemon)) },
        { pokemon, position -> pokemonToogleFavorite(pokemon, position) })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emptyListTextView = view.findViewById(R.id.emptyListTextView)
        userRecyclerView = view.findViewById(R.id.pokemonListRecyclerView)
        loader = view.findViewById(R.id.listLoader)
        userRecyclerView.setLayoutManager(GridLayoutManager(activity, 2))
        userRecyclerView.adapter = adapter

        loadPokemones()

        viewModel.filteredList.observe(viewLifecycleOwner, Observer { list ->
            if(list != null) {
                adapter.pokemonList = list
                emptyListTextView.visibility = if (list.isEmpty()) View.VISIBLE else View.INVISIBLE
            }
        })
    }

    fun loadPokemones() {
        LoaderManager().showLoader(loader)
        Handler().postDelayed({
            viewModel.setOriginalList(getDummyData())
            LoaderManager().hideLoader(loader)
        }, 1000)
    }

    private fun getDummyData() : List<Pokemon> {
        var abilities = mutableListOf("Ab 1", "Ab 2", "Ab 3", "Ab 4")
        val stats = MutableList(3) { Stat("Stat 1", "10") }
        stats.add(Stat("Stat 2", "20"))
        return listOf(
            Pokemon("1","https://pokeres.bastionbot.org/images/pokemon/1.png", "Charmander", "50", "70", abilities, stats, false, false),
            Pokemon("2","https://pokeres.bastionbot.org/images/pokemon/2.png", "Ivysaur", "60", "80", abilities, stats, true, false),
            Pokemon("3","https://pokeres.bastionbot.org/images/pokemon/3.png", "Squirtle", "70", "90", abilities, stats, false, false),
            Pokemon("4","https://pokeres.bastionbot.org/images/pokemon/4.png", "Fearow", "80", "100", abilities, stats, true, false)
        )
    }

    fun pokemonToogleFavorite(pokemon: Pokemon, position: Int){
        pokemon.favorite = !pokemon.favorite
        adapter.notifyItemChanged(position)
        viewModel.filterList()
    }

}