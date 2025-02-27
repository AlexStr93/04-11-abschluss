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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.a04_11_abschluss.model.Character
import com.example.a04_11_abschluss.viewModel.OnePieceViewModel

@Composable
fun CharacterList(viewModel: OnePieceViewModel, onCharacterClick: (Character) -> Unit) {
    val characters by viewModel.characters.collectAsState()

    Scaffold { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(8.dp)
        ) {
            items(characters) { character ->
                CharacterCard(character) {
                    onCharacterClick(character)
                }
            }
        }
    }
}

@Composable
fun CharacterCard(character: Character, onCharacterClick: (Character) -> Unit) {
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
                Text(text = "Teufelsfrucht: ${it.name} (${it.type})", style = MaterialTheme.typography.bodyMedium)
                Text(text = it.description, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}