package com.trunghoang.generalapp.ui.home

import com.miller.loadmoredbnetwork.BaseLoadMoreWithDbViewModel
import com.miller.loadmoredbnetwork.Listing
import com.trunghoang.generalapp.data.repository.MovieRepository

/**
 * Created by Hoang Trung on 18/07/2019
 */

class MovieViewModel(
    private val repository: MovieRepository
) : BaseLoadMoreWithDbViewModel() {

    override fun getInitData(): Listing {
        return repository.refreshData()
    }

    fun swapItems(from: Int, to: Int) {
        repository.swapItem(from, to)
    }
}