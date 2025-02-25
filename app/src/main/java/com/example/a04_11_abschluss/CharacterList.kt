package com.example.a04_11_abschluss.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.a04_11_abschluss.model.Character
import com.example.a04_11_abschluss.viewModel.OnePieceViewModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun CharacterList(viewModel: OnePieceViewModel, navController: NavController) {
    val characters by viewModel.characters.collectAsState()

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(characters) { character ->
            CharacterCard(character, navController)
        }
    }
}

@Composable
fun CharacterCard(character: Character, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                character.fruit?.let {
                    val characterJson = Json.encodeToString(character)
                    navController.navigate("fruitDetail/$characterJson")
                }
            },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = character.name, style = MaterialTheme.typography.headlineMedium)
            Text(text = "Größe: ${character.size}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Alter: ${character.age}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Kopfgeld: ${character.bounty}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Job: ${character.job}", style = MaterialTheme.typography.bodyMedium)


            character.crew?.let {
                Text(text = "Crew: ${it.name} (Yonko: ${it.isYonko})", style = MaterialTheme.typography.bodyMedium)
            }

            character.fruit?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Teufelsfrucht: ${it.name} (${it.type})", style = MaterialTheme.typography.bodyMedium)
                Text(text = it.description, style = MaterialTheme.typography.bodySmall)

                Image(
                    painter = rememberAsyncImagePainter(it.filename),
                    contentDescription = it.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}