package com.example.demofinal.data.room.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.demofinal.data.api.ApiService
import com.example.demofinal.data.room.db.AppDatabase
import com.example.demofinal.data.room.entities.Character
import com.example.demofinal.data.room.entities.RemoteKeys


@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediator constructor(
    private val api: ApiService,
    private val db: AppDatabase
) : RemoteMediator<Int, Character>()  {
    private val charDao = db.getCharacterDao()
    private val remoteDao = db.getRemoteKeyDao()
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Character>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevKey
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextKey
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = api.getCharacter(page = currentPage)
            val endOfPaginationReached = response.body()?.results?.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached == true) null else currentPage + 1

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    charDao.clearAll()
                    remoteDao.clearAll()
                }
                val keys = response.body()?.results?.map {
                    RemoteKeys(
                        repoId = it.id!!,
                        prevKey = prevPage,
                        nextKey = nextPage
                    )
                }
                keys?.let { remoteDao.insertRemote(list = it) }
                response.body()?.results?.let {
                    charDao.    addCharacter(character = it)
                }
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached == true)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Character>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                remoteDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Character>
    ): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { char ->
                char.id?.let { remoteDao.getRemoteKeys(id = it) }
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Character>
    ): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { char ->
                char.id?.let { remoteDao.getRemoteKeys(id = it) }
            }
    }
}