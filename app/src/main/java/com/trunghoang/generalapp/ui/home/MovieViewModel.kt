package com.trunghoang.generalapp.ui.home

import android.util.Log
import com.miller.responsivepaging.BaseLoadMoreWithDbViewModel
import com.miller.responsivepaging.Listing
import com.trunghoang.generalapp.data.model.Movie
import com.trunghoang.generalapp.data.repository.MovieRepository

/**
 * Created by Hoang Trung on 18/07/2019
 */

class MovieViewModel(
    private val repository: MovieRepository
) : BaseLoadMoreWithDbViewModel<Movie>() {

    override fun getInitData(): Listing<Movie> {
        return repository.refreshData()
    }

    fun swapItems(from: Movie, to: Movie) {
        repository.swapItem(from, to)
    }

    override fun syncDataToNetwork(item: Movie) {
    }

    override fun syncDataToNetwork(items: List<Movie>) {
        Log.d("------>", " : Sync data")
    }
}