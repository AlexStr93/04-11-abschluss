package com.example.a04_11_abschluss.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_characters")
data class FavoriteCharacter(
    @PrimaryKey val id: Int,
    val name: String,
    val size: String,
    val age: String,
    val bounty: String,
    val job: String,
    val crew: String?,
    val fruit: String?
)