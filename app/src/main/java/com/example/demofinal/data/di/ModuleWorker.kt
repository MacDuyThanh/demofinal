package com.example.demofinal.data.di

import androidx.paging.ExperimentalPagingApi
import com.example.demofinal.data.worker.ChildWorkerFactory
import com.example.demofinal.data.worker.WorkerCharacter
import com.example.demofinal.data.worker.WorkerFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@ExperimentalPagingApi
@Module
abstract class ModuleWorker {
    @Binds
    abstract fun bindWorkerFactory(workerFactory: WorkerFactory?): androidx.work.WorkerFactory?

    @Binds
    @IntoMap
    @WorkerKey(WorkerCharacter::class)
    abstract fun bindCharacterWorker(worker: WorkerCharacter.Factory?): ChildWorkerFactory?
}