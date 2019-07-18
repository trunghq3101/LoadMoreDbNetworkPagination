package com.trunghoang.generalapp.di

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

/**
 * Created by Hoang Trung on 18/07/2019
 */

val appModule = module {
    single { androidApplication().resources }
}