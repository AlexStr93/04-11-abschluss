package com.example.a04_11_abschluss

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.a04_11_abschluss.model.Fruit

@Composable
fun FruitDetailScreen(fruit: Fruit) {
    Scaffold { paddingValues -> // Scaffold bleibt für Padding und Struktur erhalten
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
                        .fillMaxHeight(0.6f), // 60% der Bildschirmhöhe für das Bild
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