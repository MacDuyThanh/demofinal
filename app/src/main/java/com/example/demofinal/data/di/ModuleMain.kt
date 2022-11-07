package com.example.demofinal.data.di

import androidx.paging.ExperimentalPagingApi
import com.example.demofinal.ui.screen1.Screen1Fragment
import com.example.demofinal.ui.screen2.Screen2Fragment
import com.example.demofinal.ui.screen3.Screen3Fragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@ExperimentalPagingApi
@Module

abstract class ModuleMain {
    @ContributesAndroidInjector
    abstract fun man1Fragment(): Screen1Fragment


    @ContributesAndroidInjector
    abstract fun man2Fragment(): Screen2Fragment

    @ContributesAndroidInjector
    abstract fun man3Fragment(): Screen3Fragment
}
