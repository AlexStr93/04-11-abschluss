package com.example.a04_11_abschluss

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a04_11_abschluss.navigation.AppNavigation
import com.example.a04_11_abschluss.viewModel.OnePieceViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: OnePieceViewModel = viewModel()
            AppNavigation(viewModel)
        }
    }
}