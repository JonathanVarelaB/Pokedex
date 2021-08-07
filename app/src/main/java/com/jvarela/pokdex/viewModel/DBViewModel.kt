package com.jvarela.pokdex.viewModel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.jvarela.pokdex.db.DatabaseManager
import com.jvarela.pokdex.db.entity.User
import com.jvarela.pokdex.db.entity.UserPokemon
import com.jvarela.pokdex.fragment.Sex
import com.jvarela.pokdex.model.entity.Pokemon
import com.jvarela.pokdex.repository.UserPokemonRepository
import com.jvarela.pokdex.repository.UserRepository
import kotlinx.coroutines.launch

open class DBViewModel(application: Application): BaseViewModel(application) {

    private lateinit var userRepo: UserRepository
    private lateinit var userPokRepo: UserPokemonRepository
    private lateinit var sharedPref: SharedPreferences

    init {
        val db = DatabaseManager.getDatabase(application.applicationContext)
        userRepo = UserRepository(db.userDao())
        userPokRepo = UserPokemonRepository(db.userPokDao())
        sharedPref = application.applicationContext.getSharedPreferences("PokedexPref", Context.MODE_PRIVATE)
    }

    // ****** USER *******
    fun existUser(email: String): LiveData<List<User>> {
        return userRepo.exist(email)
    }

    fun registerUser(email: String, name: String, sex: Sex?) = viewModelScope.launch {
        val u: User = User(email, name, sex)
        userRepo.insert(u)
        saveUserSession(u)
    }

    fun updateUser(email: String, name: String, sex: Sex?) = viewModelScope.launch {
        val u: User = User(email, name, sex)
        userRepo.update(u)
        saveUserSession(u)
    }

    private fun saveUserSession(user: User){
        sharedPref.edit {
            putString("USER_EMAIL", user.email)
            putString("USER_NAME", user.name)
            putString("USER_SEX", user.sex?.id)
            apply()
        }
    }

    fun getUserSession(): User?{
        val email: String = sharedPref.getString("USER_EMAIL", "").toString()
        val name: String = sharedPref.getString("USER_NAME", "").toString()
        return if(name?.isEmpty() || email?.isEmpty())
            null
        else
            User(email, name, if (sharedPref.getString("USER_SEX", "0") == Sex.FEMALE.id) Sex.FEMALE else Sex.MALE)
    }

    fun deleteUserSession(){
        sharedPref.edit().clear().apply()
    }

    // ****** USERPOKEMON *******

    fun insertUserPokemon(idPok: String, fav: Boolean = false, viewed: Boolean = false) = viewModelScope.launch {
        userPokRepo.insert(UserPokemon(0, getUserSession()?.email.toString(), idPok, fav, viewed))
    }

    fun getAllFavorites(): LiveData<List<UserPokemon>> {
        return userPokRepo.getAllFavorites(getUserSession()?.email.toString())
    }

    fun getFavorite(idPok: String): LiveData<List<UserPokemon>> {
        return userPokRepo.getFavorite(getUserSession()?.email.toString(), idPok)
    }

    fun getAllViewed(): LiveData<List<UserPokemon>> {
        return userPokRepo.getAllViewed(getUserSession()?.email.toString())
    }

    fun deleteFavorite(idFav: Int, idPok: String)  = viewModelScope.launch {
        userPokRepo.deleteFavorite(UserPokemon(idFav, getUserSession()?.email.toString(), idPok, true))
    }

}