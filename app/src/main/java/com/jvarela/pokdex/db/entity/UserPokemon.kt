package com.jvarela.pokdex.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserPokemon(@PrimaryKey(autoGenerate = true) val id: Int = 0, val userEmail: String, val idPokemon: String, val favorite: Boolean = false, val viewed: Boolean = false)