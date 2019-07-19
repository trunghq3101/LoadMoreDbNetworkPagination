package com.trunghoang.generalapp.data.model

import com.google.gson.annotations.SerializedName
import com.miller.loadmoredbnetwork.BaseLoadMoreResponse

/**
 * Created by Hoang Trung on 18/07/2019
 */

data class HomeSpotListResponse (
    @SerializedName("Search")
    val data: List<HomeSpot>
) : BaseLoadMoreResponse<HomeSpot, Int> {
    override fun getListData(): List<HomeSpot> {
        return data
    }
}