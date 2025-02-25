package com.example.a04_11_abschluss.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.a04_11_abschluss.FavoritesScreen
import com.example.a04_11_abschluss.data.Route
import com.example.a04_11_abschluss.model.Character
import com.example.a04_11_abschluss.ui.screens.CharacterList
import com.example.a04_11_abschluss.ui.screens.FruitDetailScreen
import com.example.a04_11_abschluss.viewModel.OnePieceViewModel
import kotlinx.serialization.json.Json

@Composable
fun AppNavigation(viewModel: OnePieceViewModel) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController, listOf(Route.Home, Route.Favorites))
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Route.Home.route,
            modifier = androidx.compose.ui.Modifier.padding(innerPadding)
        ) {
            composable(Route.Home.route) { CharacterList(viewModel, navController) }
            composable(Route.Favorites.route) { FavoritesScreen(viewModel) }
            composable(Route.FruitDetail.route) { backStackEntry ->
                val json = backStackEntry.arguments?.getString("characterJson")
                val character = json?.let {
                    try {
                        Json.decodeFromString<Character>(it)
                    } catch (e: Exception) {
                        null
                    }
                }

                character?.fruit?.let { FruitDetailScreen(it) }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController, items: List<Route>) {
    NavigationBar {
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStackEntry?.destination

        items.forEach { item ->
            val isSelected = currentDestination?.route == item.route
            NavigationBarItem(
                icon = { Icon(imageVector = if (item is Route.Home) Icons.Filled.Home else Icons.Filled.Favorite, contentDescription = null) },
                label = { Text(if (item is Route.Home) "Home" else "Favoriten") },
                selected = isSelected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}