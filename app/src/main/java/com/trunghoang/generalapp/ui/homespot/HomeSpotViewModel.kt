package com.trunghoang.generalapp.ui.homespot

import com.trunghoang.generalapp.baseloadmore.BaseLoadMoreWithDbRepository
import com.trunghoang.generalapp.baseloadmore.BaseLoadMoreWithDbViewModel
import com.trunghoang.generalapp.baseloadmore.Listing
import com.trunghoang.generalapp.data.model.HomeSpot

/**
 * Created by Hoang Trung on 18/07/2019
 */

class HomeSpotViewModel(
    private val repository: BaseLoadMoreWithDbRepository<HomeSpot, String>
) : BaseLoadMoreWithDbViewModel<HomeSpot>() {

    override fun getInitData(): Listing<HomeSpot> {
        return repository.getListData("", 10)
    }

    fun swapItems(from: Int, to: Int) {
        repository.swapItem(from, to)
    }
}