package com.miller.loadmoredbnetwork

import androidx.annotation.MainThread
import androidx.paging.DataSource
import retrofit2.Call

/**
 * Created by Hoang Trung on 19/07/2019
 */

interface ILoadMoreWithDbRepository<Item: ILoadMoreEntity, Key> {
    companion object {
        const val DEFAULT_NETWORK_PAGE_SIZE = 19
    }

    @MainThread
    fun refreshData(): Listing<Item>

    fun getDataSourceFromDb(): DataSource.Factory<Key, Item>

    fun <T: BaseLoadMoreResponse<Item, Key>>fetchDataFromNetwork(
        key: Key? = null,
        loadSize: Int? = null
    ): Call<T>

    fun insertResultIntoDb(key: Key? = null, result: BaseLoadMoreResponse<Item, Key>)

    fun getKey(): Key?

    fun <T: BaseLoadMoreResponse<Item, Key>>nextKey(response: T)
}