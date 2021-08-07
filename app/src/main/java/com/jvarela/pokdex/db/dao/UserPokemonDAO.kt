package com.jvarela.pokdex.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jvarela.pokdex.db.entity.User
import com.jvarela.pokdex.db.entity.UserPokemon

@Dao
interface UserPokemonDAO {

    @Insert
    suspend fun insert(userPok: UserPokemon)

    @Query("SELECT * FROM UserPokemon WHERE userEmail=:email AND favorite=:fav")
    fun getAllFavorites(email: String, fav: Boolean = true) : LiveData<List<UserPokemon>>

    @Query("SELECT * FROM UserPokemon WHERE userEmail=:email AND idPokemon=:idPok AND favorite=:fav")
    fun getFavorite(email: String, idPok: String, fav: Boolean = true) : LiveData<List<UserPokemon>>

    @Query("SELECT * FROM UserPokemon WHERE userEmail=:email AND viewed=:viewed")
    fun getAllViewed(email: String, viewed: Boolean = true) : LiveData<List<UserPokemon>>

    @Delete
    suspend fun deleteFavorite(userPok: UserPokemon)

}