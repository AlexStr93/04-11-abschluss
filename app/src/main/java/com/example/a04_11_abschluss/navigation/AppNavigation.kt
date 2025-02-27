package com.example.a04_11_abschluss.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.a04_11_abschluss.CharacterList
import com.example.a04_11_abschluss.FavoritesScreen
import com.example.a04_11_abschluss.FruitDetailScreen
import com.example.a04_11_abschluss.data.Route
import com.example.a04_11_abschluss.model.Fruit
import com.example.a04_11_abschluss.viewModel.OnePieceViewModel

@Composable
fun AppNavigation(viewModel: OnePieceViewModel) {
    val navController = rememberNavController()
    var selectedFruit by remember { mutableStateOf<Fruit?>(null) }

    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Route.Home.route,
            modifier = androidx.compose.ui.Modifier.padding(innerPadding)
        ) {
            composable(Route.Home.route) {
                CharacterList(viewModel, onCharacterClick = { character ->
                    selectedFruit = character.fruit
                    navController.navigate(Route.FruitDetail.route)
                })
            }
            composable(Route.Favorites.route) {
                FavoritesScreen(viewModel)
            }
            composable(Route.FruitDetail.route) {
                selectedFruit?.let { fruit ->
                    FruitDetailScreen(fruit)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    CenterAlignedTopAppBar(
        title = { Text(text = "One Piece") },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors()
    )
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(Route.Home, Route.Favorites)
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = when (item) {
                            Route.Home -> Icons.Filled.Home
                            Route.Favorites -> Icons.Filled.Favorite
                            else -> Icons.Filled.Home
                        },
                        contentDescription = null
                    )
                },
                label = { Text(item.route) },
                selected = currentRoute == item.route,
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