package com.trunghoang.generalapp.data.model

import com.google.gson.annotations.SerializedName
import com.miller.responsivepaging.BaseLoadMoreResponse

/**
 * Created by Hoang Trung on 18/07/2019
 */

data class MovieListResponse (
    @SerializedName("Search")
    val data: List<Movie>
) : BaseLoadMoreResponse<Movie> {
    override fun getListData(): List<Movie> {
        return data
    }
}