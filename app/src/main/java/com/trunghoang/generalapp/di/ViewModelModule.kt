package com.trunghoang.generalapp.di

import com.trunghoang.generalapp.ui.home.MovieViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Hoang Trung on 18/07/2019
 */

val viewModelModule = module {
    viewModel { MovieViewModel(get()) }
}