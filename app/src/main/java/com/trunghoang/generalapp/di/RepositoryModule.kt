package com.trunghoang.generalapp.di

import android.content.Context
import com.trunghoang.generalapp.baseloadmore.BaseLoadMoreWithDbRepository
import com.trunghoang.generalapp.data.local.HomeSpotDb
import com.trunghoang.generalapp.data.model.HomeSpot
import com.trunghoang.generalapp.data.repository.HomeSpotWithDbRepositoryImpl
import org.koin.dsl.module
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Created by Hoang Trung on 18/07/2019
 */

val repositoryModule = module {
    single { createHomeSpotDb(get()) }
    single { createDiskExecutor() }
    single<BaseLoadMoreWithDbRepository<HomeSpot, String>> { HomeSpotWithDbRepositoryImpl(get(), get(), get()) }
}

fun createDiskExecutor(): Executor = Executors.newSingleThreadExecutor()

fun createHomeSpotDb(context: Context): HomeSpotDb? = HomeSpotDb.create(context)