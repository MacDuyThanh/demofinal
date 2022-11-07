package com.example.demofinal.data.api

import androidx.paging.ExperimentalPagingApi
import com.example.demofinal.data.utils.Constants
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@ExperimentalPagingApi
@Module
class ModuleApi {
    @Singleton
    @Provides
    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .client(OkHttpClient.Builder().also { client ->
                val interceptor = HttpLoggingInterceptor()
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                client.addInterceptor(interceptor)
            }.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun getRetrofitService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}