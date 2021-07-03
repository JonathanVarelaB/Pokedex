package com.jvarela.pokdex.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jvarela.pokdex.R
import com.jvarela.pokdex.model.Pokemon
import com.squareup.picasso.Picasso

class DetailFragment: BaseFragment(R.layout.detail_fragment) {

    private lateinit var backButton: Button
    private lateinit var logoutButton: Button
    private lateinit var name: TextView
    private lateinit var id: TextView
    private lateinit var image: ImageView
    private lateinit var pokemon: Pokemon
    private lateinit var height: TextView
    private lateinit var weight: TextView
    private lateinit var abilities: TextView
    private lateinit var types: TextView
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCurrentFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cleanCurrentFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pokemon = args.pokemon
        logoutButton = view.findViewById(R.id.logOutDetailButton)
        backButton = view.findViewById(R.id.backButton)
        name = view.findViewById(R.id.nameTextViewDetail)
        image = view.findViewById(R.id.ImageViewDetail)
        id = view.findViewById(R.id.idTextViewDetail)
        height = view.findViewById(R.id.heightTextViewDetail)
        weight = view.findViewById(R.id.weightTextViewDetail)
        abilities = view.findViewById(R.id.abilitiesTextViewDetail)
        types = view.findViewById(R.id.typesTextViewDetail)

        showPokemonDetail()

        backButton.setOnClickListener{ back() }
        logoutButton.setOnClickListener{ findNavController().navigate(DetailFragmentDirections.actionDetailFragmentToLoginFragment()) }
    }

    override fun onBackPressed() {
        back()
    }

    fun showPokemonDetail(){
        Picasso.get().load(pokemon.imageUrl).into(image)
        name.text = pokemon.name
        id.text = if(pokemon.id.isNotEmpty()) "# " + pokemon.id else ""
        height.text = if(pokemon.height.isNotEmpty()) getString(R.string.poke_height) + ": " + pokemon.height else ""
        weight.text = if(pokemon.weight.isNotEmpty()) getString(R.string.poke_weight) + ": " + pokemon.weight else ""
        abilities.text = loadArrayInfo(pokemon.abilities, getString(R.string.poke_abilities))
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
        findNavController().navigate(DetailFragmentDirections.actionDetailFragmentToMainListFragment())
    }

}