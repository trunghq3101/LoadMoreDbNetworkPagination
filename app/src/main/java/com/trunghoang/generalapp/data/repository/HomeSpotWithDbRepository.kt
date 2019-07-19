package com.trunghoang.generalapp.data.repository

import com.miller.loadmoredbnetwork.ILoadMoreWithDbRepository
import com.trunghoang.generalapp.data.model.HomeSpot

/**
 * Created by Hoang Trung on 19/07/2019
 */

interface HomeSpotWithDbRepository: ILoadMoreWithDbRepository<HomeSpot, Int> {
    fun swapItem(from: Int, to: Int)
}