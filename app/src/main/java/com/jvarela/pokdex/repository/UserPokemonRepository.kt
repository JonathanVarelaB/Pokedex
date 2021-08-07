package com.jvarela.pokdex.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.jvarela.pokdex.db.dao.UserPokemonDAO
import com.jvarela.pokdex.db.entity.User
import com.jvarela.pokdex.db.entity.UserPokemon

class UserPokemonRepository(private val userPokDAO: UserPokemonDAO) {

    @WorkerThread
    suspend fun insert(userPok: UserPokemon){
        userPokDAO.insert(userPok)
    }

    fun getAllFavorites(email: String): LiveData<List<UserPokemon>> {
        return userPokDAO.getAllFavorites(email)
    }

    fun getFavorite(email: String, idPok: String): LiveData<List<UserPokemon>> {
        return userPokDAO.getFavorite(email, idPok)
    }

    fun getAllViewed(email: String): LiveData<List<UserPokemon>> {
        return userPokDAO.getAllViewed(email)
    }

    @WorkerThread
    suspend fun deleteFavorite(userPok: UserPokemon){
        userPokDAO.deleteFavorite(userPok)
    }

}