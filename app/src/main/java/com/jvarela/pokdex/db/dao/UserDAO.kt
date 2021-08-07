package com.jvarela.pokdex.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jvarela.pokdex.db.entity.User

@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Query("SELECT * FROM User WHERE email=:loginEmail")
    fun get(loginEmail: String) : LiveData<List<User>>

}