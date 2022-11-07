package com.example.demofinal.data.di

import androidx.paging.ExperimentalPagingApi
import androidx.work.ListenableWorker
import dagger.MapKey
import kotlin.reflect.KClass

@ExperimentalPagingApi
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class WorkerKey(val value: KClass<out ListenableWorker>)
