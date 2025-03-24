package com.example.a04_11_abschluss

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.a04_11_abschluss.model.Character
import com.example.a04_11_abschluss.model.FavoriteCharacter
import com.example.a04_11_abschluss.viewModel.FavoritesViewModel
import com.example.a04_11_abschluss.viewModel.OnePieceViewModel

@Composable
fun CharacterList(
    viewModel: OnePieceViewModel,
    favoritesViewModel: FavoritesViewModel,
    onCharacterClick: (Character) -> Unit
) {
    val characters by viewModel.characters.collectAsState()
    val favoriteCharacters by favoritesViewModel.favoriteCharacters.collectAsState(initial = emptyList())
    val errorMessage by viewModel.errorMessage.collectAsState()
    val favoriteErrorMessage by favoritesViewModel.errorMessage.collectAsState()
    val successMessage by favoritesViewModel.successMessage.collectAsState()

    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val state = rememberLazyListState()
    val firstItemVisible by remember {
        derivedStateOf {
            state.firstVisibleItemIndex == 0
        }
    }

    // Gefilterte Charakterliste basierend auf der Suche
    val filteredCharacters = characters.filter {
        it.name.contains(searchQuery.text, ignoreCase = true)
    }

    // Suchleiste nur anzeigen, wenn sie aktiviert wurde
    if (firstItemVisible) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            label = { Text("Charakter suchen...") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Suchen"
                )
            },
            singleLine = true
        )
    }

    // Fehler-Snackbar
    ErrorSnackbar(errorMessage, onDismiss = { viewModel.clearError() })
    ErrorSnackbar(favoriteErrorMessage, onDismiss = { favoritesViewModel.clearError() })

    // Erfolgsnachricht
    SuccessSnackbar(successMessage, onDismiss = { favoritesViewModel.clearSuccess() })

    LazyColumn(state = state, modifier = Modifier.fillMaxSize()) {
        items(filteredCharacters) { character ->
            val isFavorite = favoriteCharacters.any { it.id == character.id }
            CharacterCard(
                character = character,
                isFavorite = isFavorite,
                onCharacterClick = { onCharacterClick(character) },
                onFavoriteToggle = { selectedCharacter ->
                    try {
                        if (isFavorite) {
                            selectedCharacter.toFavoriteCharacter()
                                ?.let { favoritesViewModel.removeFavorite(it) }
                        } else {
                            selectedCharacter.toFavoriteCharacter()
                                ?.let { favoritesViewModel.addFavorite(it) }
                        }
                    } catch (e: Exception) {
                        favoritesViewModel.clearError()
                    }
                }
            )
        }
    }
}

@Composable
fun ErrorSnackbar(errorMessage: String?, onDismiss: () -> Unit) {
    if (errorMessage != null) {
        Snackbar(
            action = {
                TextButton(onClick = onDismiss) {
                    Text("OK")
                }
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = errorMessage)
        }
    }
}

@Composable
fun SuccessSnackbar(successMessage: String?, onDismiss: () -> Unit) {
    if (successMessage != null) {
        LaunchedEffect(successMessage) {
            kotlinx.coroutines.delay(2000)
            onDismiss()
        }

        Snackbar(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = successMessage)
        }
    }
}

// Erweiterungsfunktion für Konvertierung
fun Character.toFavoriteCharacter() = this.age?.let {
    this.size?.let { it1 ->
        this.bounty?.let { it2 ->
            this.job?.let { it3 ->
                FavoriteCharacter(
                    id = this.id,
                    name = this.name,
                    size = it1,
                    age = it,
                    bounty = it2,
                    job = it3,
                    crew = this.crew?.name,
                    fruit = this.fruit?.name
                )
            }
        }
    }
}

@Composable
fun CharacterCard(
    character: Character,
    isFavorite: Boolean,
    onCharacterClick: (Character) -> Unit,
    onFavoriteToggle: (Character) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { character.fruit?.let { onCharacterClick(character) } },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = character.name, style = MaterialTheme.typography.headlineMedium)
            Text(text = "Größe: ${character.size}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Alter: ${character.age}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Kopfgeld: ${character.bounty}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Job: ${character.job}", style = MaterialTheme.typography.bodyMedium)

            character.crew?.let {
                Text(text = "Crew: ${it.name} (Yonko: ${if (it.isYonko) "Ja" else "Nein"})", style = MaterialTheme.typography.bodyMedium)
            }

            character.fruit?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Teufelsfrucht: ${it.name}", style = MaterialTheme.typography.bodyMedium)
            }

            // Favoriten-Button
            IconButton(onClick = { onFavoriteToggle(character) }) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favorit",
                    tint = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}