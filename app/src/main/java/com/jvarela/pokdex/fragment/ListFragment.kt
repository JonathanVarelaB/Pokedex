package com.jvarela.pokdex.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.jvarela.pokdex.R
import com.jvarela.pokdex.adapter.PokemonListAdapter
import com.jvarela.pokdex.databinding.ListFragmentBinding
import com.jvarela.pokdex.ext.mapToVisibility
import com.jvarela.pokdex.model.APIViewModel
import com.jvarela.pokdex.model.entity.Pokemon

class ListFragment:Fragment(R.layout.list_fragment) {

    private var _binding: ListFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: APIViewModel by activityViewModels()

    private val adapter = PokemonListAdapter(
        { pokemon, position -> openPokemonDetail(pokemon, position)},
        { pokemon, position -> pokemonToogleFavorite(pokemon, position) })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.pokemonListRecyclerView.setLayoutManager(GridLayoutManager(activity, 2))
        binding.pokemonListRecyclerView.adapter = adapter
        setObservers()
        viewModel.getPokemon()
    }

    fun setObservers(){
        viewModel.filteredList.observe(viewLifecycleOwner, Observer { list ->
            if(list != null) {
                adapter.pokemonList = list
                binding.emptyList.visibility = if (list.isEmpty()) View.VISIBLE else View.INVISIBLE
            }
        })
        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.loader.visibility = it.mapToVisibility()
        }
    }

    fun pokemonToogleFavorite(pokemon: Pokemon, position: Int){
        if(pokemon.favorite){
            viewModel.getFavorite(pokemon.id).observe(viewLifecycleOwner){
                if(it.isNotEmpty()) {
                    viewModel.deleteFavorite(it.first().id, pokemon.id)
                    pokemon.favorite = false
                    updateList(position)
                }
            }
        }
        else {
            viewModel.insertUserPokemon(pokemon.id, fav = true)
            pokemon.favorite = true
            updateList(position)
        }
    }

    fun updateList(position: Int){
        adapter.notifyItemChanged(position)
        viewModel.filterList()
    }

    fun openPokemonDetail(pokemon: Pokemon, position: Int){
        if(!pokemon.viewed) {
            viewModel.insertUserPokemon(pokemon.id, viewed = true)
            pokemon.viewed = true
        }
        adapter.notifyItemChanged(position)
        viewModel.filterList()
        findNavController().navigate(MainListFragmentDirections.actionMainListFragmentToDetailFragment(pokemon))
    }


}