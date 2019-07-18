package com.miller.loadmoredbnetwork

import androidx.annotation.MainThread
import androidx.paging.DataSource
import androidx.paging.toLiveData
import java.util.concurrent.Executor

/**
 * Created by Hoang Trung on 15/07/2019
 */

abstract class BaseLoadMoreWithDbRepository<Item, Key>(
    private val ioExecutor: Executor,
    private val networkPageSize: Int = DEFAULT_NETWORK_PAGE_SIZE
) {
    companion object {
        private const val DEFAULT_NETWORK_PAGE_SIZE = 19
    }

    @MainThread
    fun getListData(key: Key, pageSize: Int): Listing<Item> {
        val boundaryCallback = BaseBoundaryCallback(
            key = key,
            repository = this,
            handleResponse = this::insertResultIntoDb,
            ioExecutor = ioExecutor,
            networkPageSize = networkPageSize
        )
        val livePagedList = getListDataFromDb().toLiveData(
            pageSize = pageSize,
            boundaryCallback = boundaryCallback
        )
        return Listing(
            pagedList = livePagedList,
            networkState = boundaryCallback.networkState
        )
    }

    abstract fun insertResultIntoDb(key: Key, result: BaseLoadMoreResponse<Item, Key>)

    abstract fun getListDataFromDb(): DataSource.Factory<Int, Item>

    abstract fun getListDataFromRemote(
        key: Key? = null,
        loadSize: Int? = null,
        onSuccess: (result: BaseLoadMoreResponse<Item, Key>) -> Unit,
        onError: (t: Throwable?) -> Unit)

    abstract fun swapItem(from: Int, to: Int)
}