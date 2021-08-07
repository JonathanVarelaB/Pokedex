package com.jvarela.pokdex.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jvarela.pokdex.R
import com.jvarela.pokdex.adapter.PokemonListAdapter
import com.jvarela.pokdex.adapter.StatListAdapter
import com.jvarela.pokdex.databinding.DetailFragmentBinding
import com.jvarela.pokdex.model.APIViewModel
import com.jvarela.pokdex.model.entity.Pokemon
import com.squareup.picasso.Picasso

class DetailFragment: BaseFragment(R.layout.detail_fragment) {

    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var pokemon: Pokemon
    private val args: DetailFragmentArgs by navArgs()
    private val viewModel: APIViewModel by activityViewModels()
    private val adapter = StatListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCurrentFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = DetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        cleanCurrentFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pokemon = args.pokemon
        showPokemonDetail()
        binding.statRecyclerView.adapter = adapter
        binding.backButton.setOnClickListener{ back() }
        binding.logOutDetailButton.setOnClickListener{
            viewModel.logout()
            findNavController().navigate(DetailFragmentDirections.actionDetailFragmentToLoginFragment())
        }
    }

    override fun onBackPressed() {
        back()
    }

    fun showPokemonDetail(){
        Picasso.get().load(pokemon.imageUrl).into(binding.ImageViewDetail)
        binding.nameTextViewDetail.text = pokemon.name
        binding.idTextViewDetail.text = if(pokemon.id.isNotEmpty()) "# " + pokemon.id else ""
        binding.heightTextViewDetail.text = if(pokemon.height.isNotEmpty()) getString(R.string.poke_height) + ": " + pokemon.height else ""
        binding.weightTextViewDetail.text = if(pokemon.weight.isNotEmpty()) getString(R.string.poke_weight) + ": " + pokemon.weight else ""
        binding.abilitiesTextViewDetail.text = loadArrayInfo(pokemon.abilities, getString(R.string.poke_abilities))
        adapter.statList = pokemon.stats
    }

    fun loadArrayInfo(array: List<String>, infoName: String) : String{
        var mainString: String = ""
        if (array.isNotEmpty()){
            mainString = "$infoName: "
            for (i in array.indices) {
                if(i > 0 && i < array.count())
                    mainString += ", "
                mainString += array[i]
            }
        }
        return mainString
    }

    fun back(){
        findNavController().navigate(DetailFragmentDirections.actionDetailFragmentToMainListFragment(MainNavAccess.BACK))
    }

}