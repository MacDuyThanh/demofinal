package com.example.demofinal.data.di

import androidx.paging.ExperimentalPagingApi
import com.example.demofinal.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@ExperimentalPagingApi
@Module
abstract class ModuleActivitiy {
    @ContributesAndroidInjector(modules = [ModuleMain::class])
    abstract fun contributeMainActivity(): MainActivity
}