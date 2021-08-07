package com.jvarela.pokdex.manager.api

import com.jvarela.pokdex.model.entity.Pokemon
import com.jvarela.pokdex.model.response.AllPokemonResponse
import com.jvarela.pokdex.model.response.PokemonData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiManager {

    @GET("pokemon")
    fun getAllPokemon(@Query("limit") limit: Int) : Call<AllPokemonResponse>

    @GET("pokemon/{id}/")
    fun getPokemonDetail(@Path("id") id: Int) : Call<PokemonData>
}