package com.miller.loadmoredbnetwork

import androidx.annotation.MainThread
import androidx.paging.PagedList
import com.trunghoang.generalapp.baseloadmore.PagingRequestHelper
import com.trunghoang.generalapp.baseloadmore.PagingRequestHelper.RequestType.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor

/**
 * Created by Hoang Trung on 15/07/2019
 */
class BaseBoundaryCallback<Item: BaseLoadMoreEntity, Key, ResponseType: BaseLoadMoreResponse<Item>>(
    private val repository: ILoadMoreWithDbRepository<Item, Key, ResponseType>,
    private val ioExecutor: Executor,
    private val networkPageSize: Int?,
    private val handleResponse: (response: BaseLoadMoreResponse<Item>) -> Unit
) : PagedList.BoundaryCallback<Item>(), PagingRequestHelper.Request {
    private val helper = PagingRequestHelper(ioExecutor)
    val networkState = helper.createStatusLiveData()

    @MainThread
    override fun onZeroItemsLoaded() {
        helper.runIfNotRunning(INITIAL, this)
    }

    override fun onItemAtEndLoaded(itemAtEnd: Item) {
        helper.runIfNotRunning(AFTER, this)
    }

    override fun onItemAtFrontLoaded(itemAtFront: Item) {
    }

    override fun run(callback: PagingRequestHelper.Request.Callback) {
        repository.fetchDataFromNetwork(
            key = repository.getKey(),
            loadSize = networkPageSize
        )
            .enqueue(object : Callback<ResponseType> {
                override fun onFailure(call: Call<ResponseType>, t: Throwable) {
                    callback.recordFailure(t)
                }

                override fun onResponse(
                    call: Call<ResponseType>,
                    response: Response<ResponseType>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            insertItemsIntoDb(it, callback)
                        } ?: run {
                            callback.recordFailure(Throwable(response.message()))
                        }
                    } else {
                        callback.recordFailure(Throwable(response.message()))
                    }
                }

            })
    }

    private fun insertItemsIntoDb(
        response: ResponseType,
        callback: PagingRequestHelper.Request.Callback
    ) {
        ioExecutor.execute {
            handleResponse(response)
            callback.recordSuccess()
            repository.nextKey(response)
        }
    }
}