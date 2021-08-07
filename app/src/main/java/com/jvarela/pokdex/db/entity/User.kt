package com.jvarela.pokdex.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jvarela.pokdex.fragment.Sex

@Entity
data class User(@PrimaryKey val email: String, val name: String?, val sex: Sex?)