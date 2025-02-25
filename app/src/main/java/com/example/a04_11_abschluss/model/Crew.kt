package com.example.a04_11_abschluss.model

import kotlinx.serialization.Serializable

@Serializable
data class Crew(
    val id: Int,
    val name: String,
    val description: String?,
    val status: String,
    val number: String,
    val romanName: String,
    val totalPrime: String,
    val isYonko: Boolean
)