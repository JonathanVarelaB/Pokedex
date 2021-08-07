package com.jvarela.pokdex.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jvarela.pokdex.R
import com.jvarela.pokdex.databinding.ListFragmentBinding
import com.jvarela.pokdex.model.entity.Pokemon
import com.squareup.picasso.Picasso

class PokemonListAdapter(val onPokemonClicked: ((Pokemon, Int) -> (Unit))? = null, val pokemonToogleFavorite: ((Pokemon, Int) -> (Unit))? = null): RecyclerView.Adapter<PokemonListAdapter.PokemonViewHolder>() {

    var pokemonList: List<Pokemon> = emptyList()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    inner class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var image: ImageView = itemView.findViewById(R.id.pokemonImageView)
        private var name: TextView = itemView.findViewById(R.id.nameTextView)
        private var favButton: Button = itemView.findViewById(R.id.favoriteButton)

        fun bind(model: Pokemon, position: Int) {
            image.scaleType = ImageView.ScaleType.FIT_XY
            itemView.setOnClickListener { onPokemonClicked?.invoke(model, position) }
            name.text = model.name
            Picasso.get().load(model.imageUrl).into(image)
            favButton.setCompoundDrawablesWithIntrinsicBounds(0, if (model.favorite) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24, 0, 0);
            favButton.setOnClickListener{ pokemonToogleFavorite?.invoke(model, position) }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PokemonListAdapter.PokemonViewHolder {
        val holderView = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon_cell, parent, false)
        return PokemonViewHolder(holderView)
    }

    override fun onBindViewHolder(holder: PokemonListAdapter.PokemonViewHolder, position: Int) {
        holder.bind(pokemonList[position], position)
    }

    override fun getItemCount(): Int = pokemonList.size
}