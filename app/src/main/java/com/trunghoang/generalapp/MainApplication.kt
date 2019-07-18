package com.trunghoang.generalapp

import android.app.Application
import com.trunghoang.generalapp.di.appModule
import com.trunghoang.generalapp.di.repositoryModule
import com.trunghoang.generalapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Created by Hoang Trung on 18/07/2019
 */

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(listOf(appModule, viewModelModule, repositoryModule))
        }
    }
}