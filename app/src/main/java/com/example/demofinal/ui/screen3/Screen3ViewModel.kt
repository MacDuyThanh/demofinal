package com.example.demofinal.ui.screen3

import androidx.lifecycle.*
import androidx.paging.ExperimentalPagingApi
import com.example.demofinal.data.model.Response
import com.example.demofinal.data.room.entities.Character
import com.example.demofinal.data.room.repository.CharacterRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
class Screen3ViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
) : ViewModel() {
    var page = MutableLiveData<Int>()

    //flow
    @OptIn(FlowPreview::class)
    var characterFlowResponse = page.asFlow().flatMapMerge {
        characterRepository.loadCharacterFlow(it ?: 1)
    }

    //liveData
    var characterLiveDataResponse = Transformations.switchMap(page) {
        characterRepository.loadCharacterLiveData(it ?: 1)
    }

    //worker
    var currentResponse: Response<Character>? = null
}