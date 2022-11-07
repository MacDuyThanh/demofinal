package com.example.demofinal.data.di

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import dagger.MapKey
import kotlin.reflect.KClass

@ExperimentalPagingApi
@MustBeDocumented
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
//  Các chú thích được chú thích bằng chính sách lưu giữ RUNTIME được giữ lại trong thời gian chạy
//  và có thể được truy cập trong chương trình của chúng tôi trong thời gian chạy.
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)
