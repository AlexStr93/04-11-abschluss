package com.example.a04_11_abschluss

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import coil.compose.rememberAsyncImagePainter
import com.example.a04_11_abschluss.model.Fruit

@Composable
fun FruitDetailScreen(fruit: Fruit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.onepiece_bg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { alpha = 0.2f }
        )

        Scaffold(
            containerColor = Color.Transparent
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