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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp)
            ) {
                items(characters) { character ->
                    val isFavorite = favoriteCharacters.any { it.id == character.id }
                    CharacterCard(
                        character = character,
                        isFavorite = isFavorite,
                        onCharacterClick = { onCharacterClick(character) },
                        onFavoriteToggle = { selectedCharacter ->
                            if (isFavorite) {
                                selectedCharacter.toFavoriteCharacter()
                                    ?.let { favoritesViewModel.removeFavorite(it) }
                            } else {
                                selectedCharacter.toFavoriteCharacter()
                                    ?.let { favoritesViewModel.addFavorite(it) }
                            }
                        }
                    )
                }
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