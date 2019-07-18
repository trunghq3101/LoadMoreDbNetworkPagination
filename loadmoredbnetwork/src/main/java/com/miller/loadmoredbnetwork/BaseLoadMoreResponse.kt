package com.miller.loadmoredbnetwork

/**
 * Created by Hoang Trung on 18/07/2019
 */

interface BaseLoadMoreResponse<Item, Key> {
    fun getListData(): List<Item>
    fun getKeyBefore(): Key
    fun getKeyAfter(): Key
}