package com.example.demofinal.data.room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.demofinal.data.room.dao.CharacterDao
import com.example.demofinal.data.room.dao.RemoteKeysDao
import com.example.demofinal.data.room.entities.Character
import com.example.demofinal.data.room.entities.RemoteKeys

@Database(entities = [Character::class,RemoteKeys::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getCharacterDao(): CharacterDao
    abstract fun getRemoteKeyDao(): RemoteKeysDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "character_table"
                    ).build()
                }
                return instance
            }
        }
    }

}