package com.example.a04_11_abschluss.data

import kotlinx.serialization.Serializable

@Serializable
sealed class Route(val route: String) {
    @Serializable
    data object Home : Route("home")
    @Serializable
    data object Favorites : Route("favorites")
    @Serializable
    data object FruitDetail : Route("fruitDetail/{characterJson}")
}