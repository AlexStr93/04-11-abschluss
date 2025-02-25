package com.example.a04_11_abschluss.data

import com.example.a04_11_abschluss.model.Character
import com.example.a04_11_abschluss.model.Fruit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class OnePieceRepository {
    private val apiService = RetrofitInstance.api

    fun getFruits(): Flow<List<Fruit>> = flow {
        val response = apiService.getFruits()
        if (response.isSuccessful) {
            response.body()?.let { emit(it) }
        }
    }

    fun getCharacters(): Flow<List<Character>> = flow {
        val response = apiService.getCharacters()
        if (response.isSuccessful) {
            response.body()?.let { emit(it) }
        }
    }
}