package com.example.a04_11_abschluss.model

import kotlinx.serialization.Serializable

@Serializable
data class Character(
    val id: Int,
    val name: String,
    val size: String,
    val age: String,
    val bounty: String,
    val job: String,
    val status: String,
    val crew: Crew?,
    val fruit: Fruit?
)