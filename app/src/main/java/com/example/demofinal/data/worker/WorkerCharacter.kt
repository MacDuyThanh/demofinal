package com.example.demofinal.data.worker

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.work.*
import com.example.demofinal.data.room.entities.Character
import com.example.demofinal.data.room.repository.CharacterRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.Duration
import java.util.*
import javax.inject.Inject

class WorkerCharacter(
    context: Context,
    params: WorkerParameters,
    private val repository: CharacterRepository
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        //time re-run worker
        if (runAttemptCount > 2) {
            return Result.failure()
        }

        val page = inputData.getInt(PAGE_INDEX, 1)
        kotlin.runCatching {
            repository.loadCharactersSuspend(page).datas.let {
                return Result.success(Data.Builder().apply {
                    val json = kotlin.runCatching { Gson().toJson(it) }.getOrNull()
                    this.putString(DATA, json)
                }.build())
            }
        }.getOrElse {
            Result.retry()
        }
        return Result.failure(Data.Builder().apply {
            this.putInt(ERROR, 401)
        }.build())
    }

    class Factory @Inject constructor(
        private val repository: CharacterRepository,
    ) : ChildWorkerFactory {
        override fun create(context: Context, params: WorkerParameters): CoroutineWorker {
            return WorkerCharacter(context, params, repository)
        }
    }

    companion object {
        private const val PAGE_INDEX = "PAGE_INDEX_PARAMS"
        private const val DATA = "DATA_JSON"
        private const val ERROR = "ERROR_CODE"

        fun createUUID(
            context: Context,
            page: Int,
        ): UUID {
            // initialization 1 builder for WorkerCharacter
            val workBuilder = OneTimeWorkRequest.Builder(WorkerCharacter::class.java)
            workBuilder.setInputData(Data.Builder().apply {
                this.putInt(PAGE_INDEX, page)
            }.build())


            // launch worker
            val oneTimeWorkRequest: WorkRequest = workBuilder.apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    this.setInitialDelay(Duration.ofSeconds(5))
                }
            }.build()
            WorkManager.getInstance(context).enqueue(oneTimeWorkRequest)

            return oneTimeWorkRequest.id
        }

        fun observerUUID(
            context: Context,
            uuid: UUID,
        ): LiveData<Pair<WorkInfo, MutableList<Character>?>> {
            return Transformations.map(
                WorkManager.getInstance(context).getWorkInfoByIdLiveData(uuid)
            ) {
                //convert type data into desired data
                val json = it.outputData.getString(DATA)
                val character = kotlin.runCatching {
                    val listType = object :
                        TypeToken<MutableList<Character>>() {}.type
                    val list: MutableList<Character> =
                        Gson().fromJson(json, listType)
                    list
                }.getOrNull()
                Pair<WorkInfo, MutableList<Character>?>(
                    it, character
                )
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun create(context: Context, page: Int): LiveData<Pair<WorkInfo, MutableList<Character>?>> {
            val uuid = createUUID(context, page)
            return observerUUID(context, uuid)
        }
    }
}
