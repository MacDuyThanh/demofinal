package com.example.demofinal.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.demofinal.data.room.entities.RemoteKeys

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRemote(list: List<RemoteKeys>)

    @Query("SELECT * FROM RemoteKeys WHERE repoId = :id")
    suspend fun getRemoteKeys(id: Int): RemoteKeys

    @Query("DELETE FROM RemoteKeys")
    suspend fun clearAll()
}