package com.jvarela.pokdex.model.response

import com.jvarela.pokdex.model.entity.Pokemon

data class AllPokemonResponse(

    val results: List<Pokemon>

)