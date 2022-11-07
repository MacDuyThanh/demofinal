package com.example.demofinal.data.room.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.room.*
import com.example.demofinal.data.room.entities.Character

@Dao
interface CharacterDao {
    @Query("SELECT * FROM character_table")
    fun getAllCharacter(): PagingSource<Int, Character>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: Character) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCharacter(character: List<Character>)

    @Query("DELETE FROM character_table")
    suspend fun clearAll()
}