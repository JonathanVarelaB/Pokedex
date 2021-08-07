package com.jvarela.pokdex.manager.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProxyManager {

    private val apiServer: String = "https://pokeapi.co/api/v2/"
    private val imgServer: String = "https://pokeres.bastionbot.org/images/pokemon/"

    private val retrofit: Retrofit = Retrofit.Builder().baseUrl(apiServer).addConverterFactory(GsonConverterFactory.create()).build()

    fun getImgUrl(id: Int): String{
        return "$imgServer$id.png"
    }

    fun getApiManager() : ApiManager = retrofit.create(ApiManager::class.java)

}