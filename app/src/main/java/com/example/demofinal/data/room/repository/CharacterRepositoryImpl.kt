package com.example.demofinal.data.room.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.*
import com.example.demofinal.data.api.ApiService
import com.example.demofinal.data.model.Response
import com.example.demofinal.data.room.db.AppDatabase
import com.example.demofinal.data.room.entities.Character
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalPagingApi
@Singleton
class CharacterRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val db: AppDatabase,
) : CharacterRepository {
    override fun getCharacter(): LiveData<PagingData<Character>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { CharacterSource(api, db) }
        ).liveData

    }

    override fun getCharacterDB(): Flow<PagingData<Character>> {
        val pagingSourceFactory = { db.getCharacterDao().getAllCharacter() }
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            remoteMediator = CharacterRemoteMediator(api, db),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override suspend fun loadCharacterFlow(page: Int): Flow<Response<Character>> = flow {
        emit(Response.loading())
        val list = kotlin.runCatching {
            api.getCharacter(page = page).body()?.results?.toMutableList()
        }.getOrNull()
        list?.takeIf { it.isNotEmpty() }?.run {
            emit(Response.success(this))
        } ?: kotlin.run {
            emit(Response.notFound())
        }
    }

    override fun loadCharacterLiveData(page: Int): LiveData<Response<Character>> {
        return MutableLiveData<Response<Character>>().also { liveData ->
            liveData.value = Response.loading()
            CoroutineScope(Dispatchers.IO).launch {
                val list = kotlin.runCatching {
                    api.getCharacter(page = page).body()?.results?.toMutableList()
                }.getOrNull()
                list?.takeIf { it.isNotEmpty() }?.run {
                    liveData.postValue(Response.success(this))
                } ?: kotlin.run {
                    liveData.postValue(Response.notFound())
                }
            }
        }
    }

    override suspend fun loadCharactersSuspend(page: Int): Response<Character> {
        return try {
            api.getCharacter(page).body()?.results?.toMutableList()?.run {
                Response.success(this)
            } ?: Response.notFound()
        } catch (e: Exception) {
            Response.notFound()
        }
    }

}