package com.miller.loadmoredbnetwork

import androidx.annotation.MainThread
import androidx.paging.PagedList
import com.trunghoang.generalapp.baseloadmore.PagingRequestHelper
import com.trunghoang.generalapp.baseloadmore.PagingRequestHelper.RequestType.*
import java.util.concurrent.Executor

/**
 * Created by Hoang Trung on 15/07/2019
 */

class BaseBoundaryCallback<Item, Key>(
    private val key: Key,
    private val repository: BaseLoadMoreWithDbRepository<Item, Key>,
    private val ioExecutor: Executor,
    private val networkPageSize: Int,
    private val handleResponse: (key: Key, response: BaseLoadMoreResponse<Item, Key>) -> Unit
) : PagedList.BoundaryCallback<Item>() {

    val helper = PagingRequestHelper(ioExecutor)
    val networkState = helper.createStatusLiveData()

    @MainThread
    override fun onZeroItemsLoaded() {
        helper.runIfNotRunning(INITIAL, object :
            PagingRequestHelper.Request {
            override fun run(callback: PagingRequestHelper.Request.Callback) {
                repository.getListDataFromRemote(
                    key = key,
                    loadSize = networkPageSize,
                    onError = {
                        callback.recordFailure(it)
                    },
                    onSuccess = {
                        insertItemsIntoDb(it, callback)
                    }
                )
            }

        })
    }

    override fun onItemAtEndLoaded(itemAtEnd: Item) {
        helper.runIfNotRunning(AFTER, object :
            PagingRequestHelper.Request {
            override fun run(callback: PagingRequestHelper.Request.Callback) {
                repository.getListDataFromRemote(
                    key = key,
                    loadSize = networkPageSize,
                    onError = {
                        callback.recordFailure(it)
                    },
                    onSuccess = {
                        insertItemsIntoDb(it, callback)
                    }
                )
            }

        })
    }

    override fun onItemAtFrontLoaded(itemAtFront: Item) {
    }

    private fun insertItemsIntoDb(
        response: BaseLoadMoreResponse<Item, Key>,
        callback: PagingRequestHelper.Request.Callback
    ) {
        ioExecutor.execute {
            handleResponse(key, response)
            callback.recordSuccess()
        }
    }
}