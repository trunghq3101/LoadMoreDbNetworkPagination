package com.miller.loadmoredbnetwork

import androidx.annotation.MainThread
import androidx.paging.DataSource
import androidx.paging.toLiveData
import com.miller.loadmoredbnetwork.ILoadMoreWithDbRepository.Companion.DEFAULT_NETWORK_PAGE_SIZE
import java.util.concurrent.Executor

/**
 * Created by Hoang Trung on 15/07/2019
 */
abstract class BaseLoadMoreWithDbRepository<Key, ResponseType: BaseLoadMoreResponse>(
    private val db: LoadMoreDb,
    private val ioExecutor: Executor,
    private val networkPageSize: Int? = DEFAULT_NETWORK_PAGE_SIZE
): ILoadMoreWithDbRepository<Key, ResponseType> {

    @MainThread
    override fun refreshData(): Listing {
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

    override fun getDataSourceFromDb(): DataSource.Factory<String, BaseLoadMoreEntity<BaseLoadMoreEntity.Data>> {
        return db.LoadMoreDao().getPagingData()
    }

    override fun insertResultIntoDb(result: BaseLoadMoreResponse) {
        db.runInTransaction {
            val start = db.LoadMoreDao().getNextIndex()
            val items = result.getListData().mapIndexed { index, item ->
                BaseLoadMoreEntity(indexInResponse = start + index, data = item)
            }
            db.LoadMoreDao().insert(items)
        }
    }
}