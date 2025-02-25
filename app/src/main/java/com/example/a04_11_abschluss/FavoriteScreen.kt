package com.example.a04_11_abschluss

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.a04_11_abschluss.viewModel.OnePieceViewModel

@Composable
fun FavoritesScreen(viewModel: OnePieceViewModel) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Text(
                text = "Favoriten",
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}