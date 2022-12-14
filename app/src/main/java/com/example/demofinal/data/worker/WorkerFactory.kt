package com.example.demofinal.data.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import javax.inject.Inject
import javax.inject.Provider

interface ChildWorkerFactory{
    fun create(context: Context, params: WorkerParameters): ListenableWorker
}

class WorkerFactory @Inject constructor(
    private val workerFactory: Map<Class<out ListenableWorker>,
            @JvmSuppressWildcards Provider<ChildWorkerFactory>>
): WorkerFactory()  {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters,
    ): ListenableWorker? {
        val foundEntry = workerFactory.entries.find { Class.forName(workerClassName).isAssignableFrom(it.key) }
        return if (foundEntry != null){
            val factoryProvider = foundEntry.value
            factoryProvider.get().create(appContext,workerParameters)
        }else{
            val workerClass = Class.forName(workerClassName).asSubclass(ListenableWorker::class.java)
            val constructor = workerClass.getDeclaredConstructor(
                Context::class.java,
                WorkerParameters::class.java
            )
            constructor.newInstance(appContext,workerParameters)
        }
    }
}