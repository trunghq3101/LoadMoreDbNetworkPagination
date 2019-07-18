package com.trunghoang.generalapp.data.model

import com.trunghoang.generalapp.baseloadmore.BaseLoadMoreResponse

/**
 * Created by Hoang Trung on 18/07/2019
 */

data class HomeSpotListResponse (
    val data: List<HomeSpot>
) : BaseLoadMoreResponse<HomeSpot, String> {
    override fun getListData(): List<HomeSpot> {
        return data
    }

    override fun getKeyBefore(): String {
        return ""
    }

    override fun getKeyAfter(): String {
        return ""
    }

}