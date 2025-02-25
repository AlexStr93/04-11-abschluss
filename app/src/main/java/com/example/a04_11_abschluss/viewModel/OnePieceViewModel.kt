package com.example.a04_11_abschluss.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a04_11_abschluss.data.OnePieceRepository
import com.example.a04_11_abschluss.model.Character
import com.example.a04_11_abschluss.model.Fruit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class OnePieceViewModel : ViewModel() {
    private val repository = OnePieceRepository()

    private val _characters = MutableStateFlow<List<Character>>(emptyList())
    val characters: StateFlow<List<Character>> = _characters

    private val _fruits = MutableStateFlow<List<Fruit>>(emptyList())
    val fruits: StateFlow<List<Fruit>> = _fruits

    init {
        fetchCharacters()
        fetchFruits()
    }

    private fun fetchCharacters() {
        viewModelScope.launch {
            repository.getCharacters().collectLatest { characterList ->
                _characters.value = characterList
            }
        }
    }

    private fun fetchFruits() {
        viewModelScope.launch {
            repository.getFruits().collectLatest { fruitList ->
                _fruits.value = fruitList
            }
        }
    }
}