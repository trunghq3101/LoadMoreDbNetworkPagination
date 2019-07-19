package com.miller.loadmoredbnetwork

import androidx.annotation.MainThread
import androidx.paging.DataSource
import androidx.paging.toLiveData
import com.miller.loadmoredbnetwork.ILoadMoreWithDbRepository.Companion.DEFAULT_NETWORK_PAGE_SIZE
import java.util.concurrent.Executor

/**
 * Created by Hoang Trung on 15/07/2019
 */
abstract class BaseLoadMoreWithDbRepository<Item: ILoadMoreEntity, Key>(
    private val db: BaseLoadMoreDb<Item, Key>,
    private val ioExecutor: Executor,
    private val networkPageSize: Int? = DEFAULT_NETWORK_PAGE_SIZE
): ILoadMoreWithDbRepository<Item, Key> {

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

    override fun getDataSourceFromDb(): DataSource.Factory<Key, Item> {
        return db.Dao().getPagingData()
    }

    override fun insertResultIntoDb(key: Key?, result: BaseLoadMoreResponse<Item, Key>) {
        db.runInTransaction {
            val start = db.Dao().getNextIndex()
            val items = result.getListData().mapIndexed { index, entity ->
                entity.indexInResponse = start + index
                entity
            }
            db.Dao().insert(items)
        }
    }
}