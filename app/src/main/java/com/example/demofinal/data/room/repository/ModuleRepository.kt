package com.example.demofinal.data.room.repository

import androidx.paging.ExperimentalPagingApi
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@ExperimentalPagingApi
@Module
abstract class ModuleRepository {
    @Singleton
    @Binds
    abstract fun providesRepository(characterRepositoryImpl: CharacterRepositoryImpl): CharacterRepository
}