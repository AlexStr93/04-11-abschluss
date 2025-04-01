package com.example.a04_11_abschluss

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.a04_11_abschluss.model.Fruit

@Composable
fun FruitDetailScreen(fruit: Fruit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(containerColor = Color.Transparent

        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // Nur anzeigen, wenn eine Bild-URL vorhanden ist
                if (fruit.filename.isNotBlank()) {
                    Image(
                        painter = rememberAsyncImagePainter(fruit.filename),
                        contentDescription = fruit.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.6f),
                        contentScale = ContentScale.Fit
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Text(text = "Teufelsfrucht: ${fruit.name}", style = MaterialTheme.typography.headlineMedium)
                    Text(text = "Typ: ${fruit.type}", style = MaterialTheme.typography.bodyLarge)
                    Text(text = "Beschreibung: ${fruit.description}", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}