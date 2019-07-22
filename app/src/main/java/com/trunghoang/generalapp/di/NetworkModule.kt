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
import java.util.concurrent.TimeUnit

/**
 * Created by Hoang Trung on 19/07/2019
 */

val networkModule = module {
    single { createOkHttpCache(get()) }
    single { createLoggingInterceptor() }
    single { createOkHttpClient(get(), get()) }
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

fun createOkHttpClient(
    cache: Cache?,
    logging: Interceptor
): OkHttpClient = OkHttpClient.Builder().apply {
    connectTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
    readTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
    addInterceptor(logging)
    if (cache != null) {
        cache(cache)
    }
}.build()

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

const val TIMEOUT = 10