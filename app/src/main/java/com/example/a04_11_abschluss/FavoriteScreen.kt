package com.example.a04_11_abschluss

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a04_11_abschluss.model.FavoriteCharacter
import com.example.a04_11_abschluss.viewModel.FavoritesViewModel

@Composable
fun FavoritesScreen(viewModel: FavoritesViewModel) {
    val favorites by viewModel.favoriteCharacters.collectAsState(initial = emptyList())
    var showDeleteDialog by remember { mutableStateOf(false) }
    var characterToDelete by remember { mutableStateOf<FavoriteCharacter?>(null) }
    var isGridView by remember { mutableStateOf(false) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Transparent,
        floatingActionButton = {
            FloatingActionButton(onClick = { isGridView = !isGridView }) {
                Icon(
                    painter = painterResource(
                        id = if (isGridView) R.drawable.strohhut_icon_list else R.drawable.strohhut_list
                    ),
                    contentDescription = "Ansicht wechseln",
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    ) { paddingValues ->
        Box {
            Image(
                painter = painterResource(id = R.drawable.onepiece_bg),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                if (favorites.isEmpty()) {
                    Text(
                        text = "Keine Favoriten gespeichert",
                        style = MaterialTheme.typography.bodyLarge.copy(color = Color.White, fontSize = 20.sp)
                    )
                } else {
                    if (isGridView) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(favorites.size) { index ->
                                FavoriteCharacterCard(
                                    character = favorites[index],
                                    onDeleteClick = {
                                        characterToDelete = favorites[index]
                                        showDeleteDialog = true
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(160.dp) // Einheitliche Höhe für alle Cards
                                )
                            }
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(8.dp), // Abstand zwischen Items
                            contentPadding = PaddingValues(vertical = 8.dp)    // Abstand oben und unten
                        ) {
                            items(favorites) { character ->
                                FavoriteCharacterCard(
                                    character = character,
                                    onDeleteClick = {
                                        characterToDelete = character
                                        showDeleteDialog = true
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
        if (showDeleteDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                title = { Text("Favorit löschen") },
                text = { Text("Willst du diesen Charakter aus deiner Crew entfernen?") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            characterToDelete?.let { viewModel.removeFavorite(it) }
                            showDeleteDialog = false
                        }
                    ) {
                        Text("Aye!")
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
}

@Composable
fun FavoriteCharacterCard(
    character: FavoriteCharacter,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFBE9A7))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp), // Innenabstand bleibt bestehen für Layout
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.headlineSmall.copy(color = Color(0xFF6D4C41))
                )
                character.fruit?.let {
                    Text(
                        text = "Teufelsfrucht: $it",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            IconButton(onClick = onDeleteClick) {
                Icon(
                    painter = painterResource(id = R.drawable.teufelsfrucht_icon),
                    contentDescription = "Löschen",
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}