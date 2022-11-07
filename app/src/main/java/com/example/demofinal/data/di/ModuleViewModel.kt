package com.example.demofinal.data.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import com.example.demofinal.ui.ScreenModelFactory
import com.example.demofinal.ui.screen1.Screen1ViewModel
import com.example.demofinal.ui.screen2.Screen2ViewModel
import com.example.demofinal.ui.screen3.Screen3ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@ExperimentalPagingApi
@Module
abstract class ModuleViewModel {
    @Binds
    abstract fun bindViewModelFactory(factory: ScreenModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(Screen1ViewModel::class)
    abstract fun bindViewModel1(screen1ViewModel: Screen1ViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(Screen2ViewModel::class)
    abstract fun bindViewModel2(screen2ViewModel: Screen2ViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(Screen3ViewModel::class)
    abstract fun bindViewModel3(screen3ViewModel: Screen3ViewModel): ViewModel
}