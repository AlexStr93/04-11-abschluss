package com.example.a04_11_abschluss.data

import com.example.a04_11_abschluss.model.Character
import com.example.a04_11_abschluss.model.Fruit
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// Basis-URL der API
private const val BASE_URL = "https://api.api-onepiece.com/v2/"

// Retrofit-Interface für die API-Endpunkte
interface OnePieceApiService {
    @GET("fruits/en")
    suspend fun getFruits(): Response<List<Fruit>>

    @GET("characters/en")
    suspend fun getCharacters(): Response<List<Character>>
}

// Singleton für Retrofit-Instanz
object RetrofitInstance {
    val api: OnePieceApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OnePieceApiService::class.java)
    }
}