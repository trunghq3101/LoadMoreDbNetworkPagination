package com.miller.loadmoredbnetwork

import androidx.annotation.MainThread
import androidx.paging.DataSource
import retrofit2.Call

/**
 * Created by Hoang Trung on 19/07/2019
 */

interface ILoadMoreWithDbRepository<Item: BaseLoadMoreEntity, Key, ResponseType: BaseLoadMoreResponse<Item>> {
    companion object {
        const val DEFAULT_NETWORK_PAGE_SIZE = 19
    }

    @MainThread
    fun refreshData(): Listing<Item>

    fun getDataSourceFromDb(): DataSource.Factory<Int, Item>

    fun fetchDataFromNetwork(
        key: Key? = null,
        loadSize: Int? = null
    ): Call<ResponseType>

    fun insertResultIntoDb(result: BaseLoadMoreResponse<Item>)

    fun getKey(): Key?

    fun nextKey(response: ResponseType)

    fun swapItem(from: Item, to: Item)
}