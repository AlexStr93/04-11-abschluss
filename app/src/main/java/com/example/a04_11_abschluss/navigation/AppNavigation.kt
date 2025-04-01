package com.example.a04_11_abschluss.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.a04_11_abschluss.CharacterList
import com.example.a04_11_abschluss.FavoritesScreen
import com.example.a04_11_abschluss.FruitDetailScreen
import com.example.a04_11_abschluss.R
import com.example.a04_11_abschluss.data.Route
import com.example.a04_11_abschluss.model.Fruit
import com.example.a04_11_abschluss.viewModel.FavoritesViewModel
import com.example.a04_11_abschluss.viewModel.OnePieceViewModel

@Composable
fun AppNavigation(
    viewModel: OnePieceViewModel,
    favoritesViewModel: FavoritesViewModel = viewModel()
) {
    val navController = rememberNavController()
    var selectedFruit by remember { mutableStateOf<Fruit?>(null) }

    Scaffold(

        topBar = { TopBar(navController) },
        bottomBar = { BottomNavigationBar(navController) },
        modifier = Modifier.background(Color.Transparent)
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.onepiece_bg),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                alpha = 0.5f
            )

            NavHost(
                navController = navController,
                startDestination = Route.Home.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Route.Home.route) {
                    CharacterList(
                        viewModel = viewModel,
                        favoritesViewModel = favoritesViewModel,
                        onCharacterClick = { character ->
                            selectedFruit = character.fruit
                            navController.navigate(Route.FruitDetail.route)
                        }
                    )
                }
                composable(Route.Favorites.route) {
                    FavoritesScreen(favoritesViewModel)
                }
                composable(Route.FruitDetail.route) {
                    selectedFruit?.let { fruit ->
                        FruitDetailScreen(fruit)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavHostController) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    val title = when (currentRoute) {
        Route.Home.route -> "One Piece"
        Route.Favorites.route -> "Favoriten"
        Route.FruitDetail.route -> "Teufelsfrüchte"
        else -> "One Piece"
    }

    CenterAlignedTopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            if (currentRoute == Route.FruitDetail.route) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Zurück")
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
    )
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(Route.Home, Route.Favorites)
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar (
        containerColor = Color.Transparent
    ) {
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