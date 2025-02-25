package com.example.a04_11_abschluss.model

import kotlinx.serialization.Serializable

@Serializable
data class Fruit(
    val id: Int,
    val name: String,
    val description: String,
    val type: String,
    val filename: String,
    val romanName: String,
    val technicalFile: String
)