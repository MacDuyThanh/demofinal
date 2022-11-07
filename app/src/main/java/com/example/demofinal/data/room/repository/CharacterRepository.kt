package com.example.demofinal.data.room.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.demofinal.data.model.Response
import com.example.demofinal.data.room.entities.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun getCharacter(): LiveData<PagingData<Character>>
    fun getCharacterDB(): Flow<PagingData<Character>>

    suspend fun loadCharacterFlow(page: Int): Flow<Response<Character>>
    fun loadCharacterLiveData(page: Int): LiveData<Response<Character>>
    suspend fun loadCharactersSuspend(page: Int): Response<Character>
}