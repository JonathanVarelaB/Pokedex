package com.jvarela.pokdex.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.jvarela.pokdex.db.dao.UserDAO
import com.jvarela.pokdex.db.entity.User

class UserRepository(private val userDAO: UserDAO) {

    @WorkerThread
    suspend fun insert(user: User){
        userDAO.insert(user)
    }

    fun exist(loginEmail: String): LiveData<List<User>> {
        return userDAO.get(loginEmail)
    }

    @WorkerThread
    suspend fun update(user: User){
        userDAO.insert(user)
    }


}