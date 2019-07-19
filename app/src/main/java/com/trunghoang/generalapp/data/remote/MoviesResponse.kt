package com.trunghoang.generalapp.data.remote

import com.google.gson.annotations.SerializedName
import com.miller.loadmoredbnetwork.BaseLoadMoreResponse
import com.trunghoang.generalapp.data.model.HomeSpot

/**
 * Created by Hoang Trung on 19/07/2019
 */

data class MoviesResponse(
    @SerializedName("Search")
    val search: List<HomeSpot>
) : BaseLoadMoreResponse<HomeSpot, Int> {
    override fun getListData() = search
}