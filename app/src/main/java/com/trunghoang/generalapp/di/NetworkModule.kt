package com.trunghoang.generalapp.di

import android.content.Context
import com.trunghoang.generalapp.BuildConfig
import com.trunghoang.generalapp.data.remote.ApiService
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Hoang Trung on 19/07/2019
 */

val networkModule = module {
    single { createOkHttpCache(get()) }
    single { createLoggingInterceptor() }
    single { createAppRetrofit(get()) }
    single { createApiService(get()) }
}

fun createOkHttpCache(context: Context): Cache =
    Cache(context.cacheDir, (10 * 1024 * 1024).toLong())

fun createLoggingInterceptor(): Interceptor =
    HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
        else HttpLoggingInterceptor.Level.NONE
    }

fun createAppRetrofit(
    okHttpClient: OkHttpClient
): Retrofit = Retrofit.Builder().apply {
    addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    addConverterFactory(GsonConverterFactory.create())
    baseUrl(BuildConfig.BASE_URL)
    client(okHttpClient)
}.build()

fun createApiService(
    retrofit: Retrofit
): ApiService = retrofit.create(ApiService::class.java)