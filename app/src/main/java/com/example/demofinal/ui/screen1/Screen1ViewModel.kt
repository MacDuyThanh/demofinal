package com.example.demofinal.ui.screen1

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.demofinal.data.room.entities.Character
import com.example.demofinal.data.room.repository.CharacterRepository
import javax.inject.Inject

@ExperimentalPagingApi
class Screen1ViewModel @Inject constructor(private val characterRepository: CharacterRepository) : ViewModel() {
    val characterLiveData: LiveData<PagingData<Character>> =
        characterRepository.getCharacter().cachedIn(viewModelScope)
}