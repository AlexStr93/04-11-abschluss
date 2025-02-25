package com.example.a04_11_abschluss.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.a04_11_abschluss.viewModel.OnePieceViewModel

@Composable
fun FruitList(viewModel: OnePieceViewModel = viewModel()) {
    val fruits by viewModel.fruits.collectAsState(initial = emptyList())

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        item {
            Text(
                text = "Devil Fruits",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        items(fruits) { fruit ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Name: ${fruit.name}", style = MaterialTheme.typography.bodyLarge)
                    Text(text = "Type: ${fruit.type}")

                    Text(text = "Description: ${fruit.description ?: "Keine Beschreibung verfügbar"}")

                    // Bild der Teufelsfrucht anzeigen
                    Image(
                        painter = rememberAsyncImagePainter(fruit.filename),
                        contentDescription = fruit.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}