package com.example.a04_11_abschluss.data

import androidx.room.*
import com.example.a04_11_abschluss.model.FavoriteCharacter
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteCharacterDao {
    @Query("SELECT * FROM favorite_characters")
    fun getAllFavorites(): Flow<List<FavoriteCharacter>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(character: FavoriteCharacter)

    @Delete
    suspend fun deleteFavorite(character: FavoriteCharacter)

    @Query("DELETE FROM favorite_characters WHERE id = :characterId")
    suspend fun deleteFavoriteById(characterId: Int)
}