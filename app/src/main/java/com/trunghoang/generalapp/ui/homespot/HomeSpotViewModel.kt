package com.trunghoang.generalapp.ui.homespot

import com.miller.loadmoredbnetwork.BaseLoadMoreWithDbViewModel
import com.miller.loadmoredbnetwork.Listing
import com.trunghoang.generalapp.data.model.HomeSpot
import com.trunghoang.generalapp.data.repository.HomeSpotWithDbRepository

/**
 * Created by Hoang Trung on 18/07/2019
 */

class HomeSpotViewModel(
    private val repository: HomeSpotWithDbRepository
) : BaseLoadMoreWithDbViewModel<HomeSpot>() {

    override fun getInitData(): Listing<HomeSpot> {
        return repository.refreshData()
    }

    fun swapItems(from: Int, to: Int) {
        repository.swapItem(from, to)
    }
}