package com.example.a04_11_abschluss.model

import kotlinx.serialization.Serializable

@Serializable
data class Character(
    val id: Int,
    val name: String = "Unbekannt",
    val size: String? = null,
    val age: String? = null,
    val bounty: String? = null,
    val job: String? = null,
    val status: String? = null,
    val crew: Crew? = null,
    val fruit: Fruit? = null
)