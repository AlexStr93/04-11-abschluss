package com.example.a04_11_abschluss

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.a04_11_abschluss.model.FavoriteCharacter
import com.example.a04_11_abschluss.viewModel.FavoritesViewModel

@Composable
fun FavoritesScreen(viewModel: FavoritesViewModel) {
    val favorites by viewModel.favoriteCharacters.collectAsState(initial = emptyList())

    var showDeleteDialog by remember { mutableStateOf(false) }
    var characterToDelete by remember { mutableStateOf<FavoriteCharacter?>(null) }
    var isGridView by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { isGridView = !isGridView }) {
                Icon(
                    imageVector = if (isGridView) Icons.AutoMirrored.Filled.List else Icons.Filled.GridView,
                    contentDescription = "Ansicht wechseln"
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            if (favorites.isEmpty()) {
                Text(text = "Keine Favoriten gespeichert", style = MaterialTheme.typography.bodyMedium)
            } else {
                if (isGridView) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(4.dp)
                    ) {
                        items(favorites.size) { index ->
                            FavoriteCharacterCard(favorites[index], onDeleteClick = {
                                characterToDelete = favorites[index]
                                showDeleteDialog = true
                            })
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(favorites) { character ->
                            FavoriteCharacterCard(character, onDeleteClick = {
                                characterToDelete = character
                                showDeleteDialog = true // Bestätigungsdialog anzeigen
                            })
                        }
                    }
                }
            }
        }
    }

    // Bestätigungsdialog anzeigen, wenn showDeleteDialog == true
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Favorit löschen") },
            text = { Text("Sind Sie sicher, dass Sie diesen Favoriten entfernen möchten?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        characterToDelete?.let { viewModel.removeFavorite(it) }
                        showDeleteDialog = false
                    }
                ) {
                    Text("Ja")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Abbrechen")
                }
            }
        )
    }
}

@Composable
fun FavoriteCharacterCard(character: FavoriteCharacter, onDeleteClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = character.name, style = MaterialTheme.typography.headlineSmall)
                character.fruit?.let { Text(text = "Teufelsfrucht: $it", style = MaterialTheme.typography.bodyMedium) }
            }

            IconButton(onClick = { onDeleteClick() }) {
                Icon(imageVector = Icons.Filled.Delete, contentDescription = "Löschen")
            }
        }
    }
}