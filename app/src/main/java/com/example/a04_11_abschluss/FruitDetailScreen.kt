package com.example.a04_11_abschluss.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.a04_11_abschluss.model.Fruit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FruitDetailScreen(fruit: Fruit) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = fruit.name) })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(text = "Teufelsfrucht: ${fruit.name}", style = MaterialTheme.typography.headlineMedium)
            Text(text = "Typ: ${fruit.type}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Beschreibung: ${fruit.description}", style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = rememberAsyncImagePainter(fruit.filename),
                contentDescription = fruit.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}