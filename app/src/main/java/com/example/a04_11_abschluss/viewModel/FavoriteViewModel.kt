package com.example.a04_11_abschluss.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.a04_11_abschluss.data.AppDatabase
import com.example.a04_11_abschluss.data.FavoriteCharacterDao
import com.example.a04_11_abschluss.model.FavoriteCharacter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {
    private val favoriteCharacterDao: FavoriteCharacterDao =
        AppDatabase.getDatabase(application).favoriteCharacterDao()

    val favoriteCharacters: Flow<List<FavoriteCharacter>> = favoriteCharacterDao.getAllFavorites()

    private val _successMessage = MutableStateFlow<String?>(null)
    val successMessage = _successMessage.asStateFlow()

    // Fehlerstatus
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()


    fun addFavorite(character: FavoriteCharacter) {
        viewModelScope.launch {
            try {
                favoriteCharacterDao.insertFavorite(character)
                _successMessage.value = "Favorit hinzugefügt: ${character.name}"
                _errorMessage.value = null // Kein Fehler
            } catch (e: Exception) {
                _errorMessage.value = "Fehler beim Hinzufügen des Favoriten: ${e.localizedMessage}"
            }
        }
    }

    fun removeFavorite(character: FavoriteCharacter) {
        viewModelScope.launch {
            try {
                favoriteCharacterDao.deleteFavoriteById(character.id)
                _errorMessage.value = null // Erfolgreich -> Kein Fehler
            } catch (e: Exception) {
                _errorMessage.value = "Fehler beim Entfernen des Favoriten: ${e.localizedMessage}"
            }
        }
    }

    // Methode, um die Fehlermeldung zu löschen (z. B. nach dem Anzeigen in der UI)
    fun clearError() {
        _errorMessage.value = null
    }
    fun clearSuccess() {
        _successMessage.value = null
    }
}
