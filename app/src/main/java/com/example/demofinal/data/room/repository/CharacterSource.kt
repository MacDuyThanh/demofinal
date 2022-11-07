package com.example.demofinal.data.room.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.demofinal.data.api.ApiService
import com.example.demofinal.data.room.db.AppDatabase
import com.example.demofinal.data.room.entities.Character
import java.io.IOException

class CharacterSource(
    private val api: ApiService,
    private val db: AppDatabase,
) : PagingSource<Int, Character>() {
    private val DEFAULT_PAGE_INDEX = 1
    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return DEFAULT_PAGE_INDEX
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        return try {
            val page = params.key ?: DEFAULT_PAGE_INDEX
            val response = api.getCharacter(page)
            response.body()?.results?.forEach {
                db.getCharacterDao().insertCharacter(it)
            }

            LoadResult.Page(
                data = response.body()?.results as MutableList<Character>,
                prevKey = page.minus(1).takeIf { it > 0 },
                nextKey = page.plus(1).takeIf {
                    response.body()?.results!!.isNotEmpty()
                }
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}