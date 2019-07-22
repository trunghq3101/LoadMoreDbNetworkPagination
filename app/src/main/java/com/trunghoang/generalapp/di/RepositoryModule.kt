package com.trunghoang.generalapp.di

import android.content.Context
import com.miller.loadmoredbnetwork.LoadMoreDb
import com.trunghoang.generalapp.data.repository.MovieRepository
import com.trunghoang.generalapp.data.repository.MovieRepositoryImpl
import org.koin.dsl.module
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Created by Hoang Trung on 18/07/2019
 */

val repositoryModule = module {
    single { createHomeSpotDb(get()) }
    single { createDiskExecutor() }
    single<MovieRepository> { MovieRepositoryImpl(get(), get(), get()) }
}

fun createDiskExecutor(): Executor = Executors.newSingleThreadExecutor()

fun createHomeSpotDb(context: Context): LoadMoreDb? = LoadMoreDb.create(context)