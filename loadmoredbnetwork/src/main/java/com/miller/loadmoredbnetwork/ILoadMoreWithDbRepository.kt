package com.miller.loadmoredbnetwork

import androidx.annotation.MainThread
import androidx.paging.DataSource
import retrofit2.Call

/**
 * Created by Hoang Trung on 19/07/2019
 */

interface ILoadMoreWithDbRepository<Key, ResponseType: BaseLoadMoreResponse> {
    companion object {
        const val DEFAULT_NETWORK_PAGE_SIZE = 19
    }

    @MainThread
    fun refreshData(): Listing

    fun getDataSourceFromDb(): DataSource.Factory<String, BaseLoadMoreEntity<BaseLoadMoreEntity.Data>>

    fun fetchDataFromNetwork(
        key: Key? = null,
        loadSize: Int? = null
    ): Call<ResponseType>

    fun insertResultIntoDb(result: BaseLoadMoreResponse)

    fun getKey(): Key?

    fun nextKey(response: ResponseType)
}