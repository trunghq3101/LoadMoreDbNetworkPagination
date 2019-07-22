package com.trunghoang.generalapp.data.model

import com.google.gson.annotations.SerializedName
import com.miller.loadmoredbnetwork.BaseLoadMoreEntity

/**
 * Created by Hoang Trung on 18/07/2019
 */

data class Movie(
    @SerializedName("Title")
    val title: String? = null,
    @SerializedName("Year")
    val year: String? = null,
    @SerializedName("Poster")
    val poster: String? = null
): BaseLoadMoreEntity.Data