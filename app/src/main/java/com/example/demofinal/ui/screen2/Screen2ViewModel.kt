package com.example.demofinal.ui.screen2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.demofinal.data.room.entities.Character
import com.example.demofinal.data.room.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class Screen2ViewModel @Inject constructor(
private val characterRepository: CharacterRepository
) : ViewModel() {
    val characterFlow: Flow<PagingData<Character>> = characterRepository.getCharacterDB()
        .cachedIn(viewModelScope)
}