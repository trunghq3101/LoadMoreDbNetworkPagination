package com.miller.loadmoredbnetwork

import android.graphics.Movie
import androidx.annotation.MainThread
import androidx.paging.DataSource
import androidx.paging.toLiveData
import com.miller.loadmoredbnetwork.ILoadMoreWithDbRepository.Companion.DEFAULT_NETWORK_PAGE_SIZE
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Created by Hoang Trung on 15/07/2019
 */
abstract class BaseLoadMoreWithDbRepository<Item: BaseLoadMoreEntity, Key, ResponseType: BaseLoadMoreResponse<Item>>(
    private val db: LoadMoreDb<Item>,
    private val ioExecutor: Executor,
    private val networkPageSize: Int? = DEFAULT_NETWORK_PAGE_SIZE
): ILoadMoreWithDbRepository<Item, Key, ResponseType> {

    @MainThread
    override fun refreshData(): Listing<Item> {
        val boundaryCallback = BaseBoundaryCallback(
            repository = this,
            ioExecutor = ioExecutor,
            networkPageSize = networkPageSize,
            handleResponse = this::insertResultIntoDb
        )
        val livePagedList = getDataSourceFromDb().toLiveData(
            pageSize = networkPageSize ?: DEFAULT_NETWORK_PAGE_SIZE,
            boundaryCallback = boundaryCallback
        )
        return Listing(
            pagedList = livePagedList,
            networkState = boundaryCallback.networkState
        )
    }

    override fun getDataSourceFromDb(): DataSource.Factory<Int, Item> {
        return db.LoadMoreDao().getPagingData()
    }

    override fun insertResultIntoDb(result: BaseLoadMoreResponse<Item>) {
        db.runInTransaction {
            val start = db.LoadMoreDao().getNextIndex()
            val items = result.getListData().mapIndexed { index, entity ->
                entity.indexInResponse = start + index
                entity
            }
            db.LoadMoreDao().insert(items)
        }
    }

    override fun swapItem(from: Item, to: Item) {
        Executors.newSingleThreadExecutor().execute {
            val start = from.indexInResponse
            val end = to.indexInResponse

            val items = db.LoadMoreDao().getBetween(start, end)
            if (start < end) {
                for (i in 0 until end - start) {
                    Collections.swap(items, i, i+1)
                }
            } else {
                for (i in end - start downTo 1) {
                    Collections.swap(items, i, i - 1)
                }
            }
            db.LoadMoreDao().updateAll(items)
        }
    }
}