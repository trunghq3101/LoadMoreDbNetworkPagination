package com.trunghoang.generalapp.data.repository

import android.content.Context
import androidx.paging.DataSource
import com.google.gson.Gson
import com.trunghoang.generalapp.R
import com.trunghoang.generalapp.baseloadmore.BaseLoadMoreResponse
import com.trunghoang.generalapp.baseloadmore.BaseLoadMoreWithDbRepository
import com.trunghoang.generalapp.data.local.HomeSpotDb
import com.trunghoang.generalapp.data.model.HomeSpot
import com.trunghoang.generalapp.data.model.HomeSpotListResponse
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Created by Hoang Trung on 18/07/2019
 */

class HomeSpotWithDbRepositoryImpl(
    private val context: Context,
    private val ioExecutor: Executor,
    private val db: HomeSpotDb
) : BaseLoadMoreWithDbRepository<HomeSpot, String>(ioExecutor = ioExecutor) {

    override fun insertResultIntoDb(key: String, result: BaseLoadMoreResponse<HomeSpot, String>) {
        db.runInTransaction {
            val start = db.HomeSpotDao().getNextIndex()
            val items = result.getListData().mapIndexed { index, homeSpot ->
                homeSpot.indexInResponse = start + index
                homeSpot
            }
            db.HomeSpotDao().insert(items)
        }
    }

    override fun getListDataFromDb(): DataSource.Factory<Int, HomeSpot> {
        return db.HomeSpotDao().getSpots()
    }

    override fun getListDataFromRemote(
        key: String?,
        loadSize: Int?,
        onSuccess: (result: BaseLoadMoreResponse<HomeSpot, String>) -> Unit,
        onError: (t: Throwable?) -> Unit
    ) {
        val objectArrayString = context.resources.openRawResource(R.raw.home).bufferedReader().use { it.readText() }
        val objectArray = Gson().fromJson(objectArrayString, HomeSpotListResponse::class.java)
        onSuccess(objectArray)
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