package com.miller.loadmoredbnetwork

/**
 * Created by Hoang Trung on 18/07/2019
 */

interface BaseLoadMoreResponse {
    fun getListData(): List<BaseLoadMoreEntity.Data>
}