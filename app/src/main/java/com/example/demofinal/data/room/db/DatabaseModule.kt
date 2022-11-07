package com.example.demofinal.data.room.db

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@ExperimentalPagingApi
@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun getAppDatabase(context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }
}