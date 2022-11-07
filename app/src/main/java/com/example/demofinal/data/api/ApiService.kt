package com.example.demofinal.data.api

import com.example.demofinal.data.room.entities.CharacterList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("character")
    suspend fun getCharacter(@Query("page") page: Int): Response<CharacterList>
}