package com.example.demofinal.data.component

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import com.example.demofinal.data.di.AppModule
import com.example.demofinal.ui.MyApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@ExperimentalPagingApi
@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class])
interface AppComponent : AndroidInjector<MyApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    override fun inject(app: MyApplication)
}