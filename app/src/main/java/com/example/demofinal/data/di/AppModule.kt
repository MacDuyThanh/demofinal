package com.example.demofinal.data.di

import androidx.paging.ExperimentalPagingApi
import com.example.demofinal.data.api.ModuleApi
import com.example.demofinal.data.room.db.DatabaseModule
import com.example.demofinal.data.room.repository.ModuleRepository
import dagger.Module

@ExperimentalPagingApi
@Module(
    includes = [
        // module of Activity && ViewModel
        ModuleActivitiy::class,
        ModuleViewModel::class,

        // module of data
        ModuleApi::class,
        ModuleRepository::class,
        DatabaseModule::class,

        // module of worker
        ModuleWorker::class
    ]
)
class AppModule