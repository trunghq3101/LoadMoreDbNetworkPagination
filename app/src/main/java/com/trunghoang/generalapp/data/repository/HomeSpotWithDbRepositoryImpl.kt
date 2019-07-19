package com.trunghoang.generalapp.data.repository

import androidx.paging.DataSource
import com.miller.loadmoredbnetwork.BaseBoundaryCallback
import com.miller.loadmoredbnetwork.BaseLoadMoreResponse
import com.miller.loadmoredbnetwork.BaseLoadMoreWithDbRepository
import com.trunghoang.generalapp.data.local.HomeSpotDb
import com.trunghoang.generalapp.data.model.HomeSpot
import com.trunghoang.generalapp.data.model.HomeSpotListResponse
import com.trunghoang.generalapp.data.remote.ApiService
import retrofit2.Call
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Created by Hoang Trung on 18/07/2019
 */

class HomeSpotWithDbRepositoryImpl(
    private val ioExecutor: Executor,
    private val db: HomeSpotDb,
    private val apiService: ApiService
) : BaseLoadMoreWithDbRepository<HomeSpot, Int>(
    db = db,
    ioExecutor = ioExecutor,
    networkPageSize = 10
), HomeSpotWithDbRepository {

    private var key = 1

    override fun getKey(): Int? {
        return key
    }

    override fun <T : BaseLoadMoreResponse<HomeSpot, Int>> nextKey(response: T) {
        key ++
    }

    override fun <T : BaseLoadMoreResponse<HomeSpot, Int>> fetchDataFromNetwork(
        key: Int?,
        loadSize: Int?
    ): Call<T> {
        return apiService.searchMovies("Avenger", key) as Call<T>
    }

    /*override fun insertResultIntoDb(key: Int?, result: BaseLoadMoreResponse<HomeSpot, Int>) {
        db.runInTransaction {
            val start = db.HomeSpotDao().getNextIndex()
            val items = result.getListData().mapIndexed { index, homeSpot ->
                homeSpot.indexInResponse = start + index
                homeSpot
            }
            db.HomeSpotDao().insert(items)
        }
    }*/

    override fun getDataSourceFromDb(): DataSource.Factory<Int, HomeSpot> {
        return db.HomeSpotDao().getPagingData()
    }

    override fun swapItem(from: Int, to: Int) {
        Executors.newSingleThreadExecutor().execute {
            db.runInTransaction {
                val item1 = db.HomeSpotDao().getSpot(from)
                val item2 = db.HomeSpotDao().getSpot(to)
                val newItem1 = item1.copy()
                newItem1.indexInResponse = item2.indexInResponse
                val newItem2 = item2.copy()
                newItem2.indexInResponse = item1.indexInResponse
                db.HomeSpotDao().updateSpot(newItem1)
                db.HomeSpotDao().updateSpot(newItem2)
            }
        }
    }

}