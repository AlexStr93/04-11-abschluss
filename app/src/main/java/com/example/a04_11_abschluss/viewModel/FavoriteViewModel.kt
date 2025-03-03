package com.example.a04_11_abschluss.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.a04_11_abschluss.data.AppDatabase
import com.example.a04_11_abschluss.data.FavoriteCharacterDao
import com.example.a04_11_abschluss.model.FavoriteCharacter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {
    private val favoriteCharacterDao: FavoriteCharacterDao =
        AppDatabase.getDatabase(application).favoriteCharacterDao()

    val favoriteCharacters: Flow<List<FavoriteCharacter>> = favoriteCharacterDao.getAllFavorites()

    fun addFavorite(character: FavoriteCharacter) {
        viewModelScope.launch {
            favoriteCharacterDao.insertFavorite(character)
        }
    }

    fun removeFavorite(character: FavoriteCharacter) {
        viewModelScope.launch {
            favoriteCharacterDao.deleteFavoriteById(character.id)
        }
    }
}