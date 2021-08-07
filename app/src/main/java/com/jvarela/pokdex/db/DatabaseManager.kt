package com.jvarela.pokdex.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jvarela.pokdex.db.dao.UserDAO
import com.jvarela.pokdex.db.dao.UserPokemonDAO
import com.jvarela.pokdex.db.entity.User
import com.jvarela.pokdex.db.entity.UserPokemon

@Database(entities = [User::class, UserPokemon::class], version = 2, exportSchema = false)
public abstract class DatabaseManager: RoomDatabase() {

    abstract fun userDao(): UserDAO
    abstract fun userPokDao(): UserPokemonDAO

    companion object{
        @Volatile
        private var INSTANCE: DatabaseManager? = null

        fun getDatabase(context: Context): DatabaseManager{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseManager::class.java,
                    "database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}