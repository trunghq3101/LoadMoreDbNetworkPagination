package com.trunghoang.generalapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.miller.loadmoredbnetwork.BaseLoadMoreEntity

/**
 * Created by Hoang Trung on 18/07/2019
 */

@Entity(tableName = "page_data")
data class Movie(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    @SerializedName("Title")
    val title: String? = null,
    @SerializedName("Year")
    val year: String? = null,
    @SerializedName("Poster")
    val poster: String? = null
): BaseLoadMoreEntity {
    override var indexInResponse: Int = -1
}