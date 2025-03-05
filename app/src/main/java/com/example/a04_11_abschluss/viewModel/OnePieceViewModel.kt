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

    // Fehlerstatus
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        fetchCharacters()
        fetchFruits()
    }

    private fun fetchCharacters() {
        viewModelScope.launch {
            try {
                repository.getCharacters().collectLatest { characterList ->
                    _characters.value = characterList
                    _errorMessage.value = null // Erfolgreich -> Kein Fehler
                }
            } catch (e: Exception) {
                _errorMessage.value = "Fehler beim Abrufen der Charaktere: ${e.localizedMessage}"
            }
        }
    }

    private fun fetchFruits() {
        viewModelScope.launch {
            try {
                repository.getFruits().collectLatest { fruitList ->
                    _fruits.value = fruitList
                    _errorMessage.value = null // Erfolgreich -> Kein Fehler
                }
            } catch (e: Exception) {
                _errorMessage.value = "Fehler beim Abrufen der Teufelsfrüchte: ${e.localizedMessage}"
            }
        }
    }

    // Methode zum Löschen der Fehlermeldung
    fun clearError() {
        _errorMessage.value = null
    }
}