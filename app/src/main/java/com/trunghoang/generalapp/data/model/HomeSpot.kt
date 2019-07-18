package com.trunghoang.generalapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Hoang Trung on 18/07/2019
 */

@Entity(tableName = "spots")
data class HomeSpot(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    val title: String? = null,
    val image: String? = null,
    val price: String? = null,
    val type: String? = null
) {
    var indexInResponse: Int = -1
}