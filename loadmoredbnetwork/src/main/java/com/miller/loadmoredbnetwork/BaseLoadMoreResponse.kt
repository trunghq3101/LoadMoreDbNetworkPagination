package com.miller.loadmoredbnetwork

/**
 * Created by Hoang Trung on 18/07/2019
 */

interface BaseLoadMoreResponse<Item: ILoadMoreEntity, Key> {
    fun getListData(): List<Item>
}