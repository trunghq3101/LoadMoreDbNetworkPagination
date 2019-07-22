package com.miller.loadmoredbnetwork

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Hoang Trung on 22/07/2019
 */

@Entity(tableName = "page_data")
data class BaseLoadMoreEntity<T>(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    var indexInResponse: Int? = null,
    var data: T? = null
) {
    interface Data
}