package com.trunghoang.generalapp.data.repository

import com.miller.loadmoredbnetwork.BaseLoadMoreWithDbRepository
import com.miller.loadmoredbnetwork.LoadMoreDb
import com.trunghoang.generalapp.data.model.MovieListResponse
import com.trunghoang.generalapp.data.remote.ApiService
import retrofit2.Call
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Created by Hoang Trung on 18/07/2019
 */

class MovieRepositoryImpl(
    private val ioExecutor: Executor,
    private val db: LoadMoreDb,
    private val apiService: ApiService
) : BaseLoadMoreWithDbRepository<Int, MovieListResponse>(
    db = db,
    ioExecutor = ioExecutor,
    networkPageSize = 10
), MovieRepository {

    private var key = 1

    override fun getKey(): Int? {
        return key
    }

    override fun nextKey(response: MovieListResponse) {
        key ++
    }

    override fun fetchDataFromNetwork(
        key: Int?,
        loadSize: Int?
    ): Call<MovieListResponse> {
        return apiService.searchMovies("Avenger", key)
    }

    override fun swapItem(from: Int, to: Int) {
        Executors.newSingleThreadExecutor().execute {
            db.runInTransaction {
                val item1 = db.LoadMoreDao().getItem(from)
                val item2 = db.LoadMoreDao().getItem(to)
                val newItem1 = item1.copy()
                newItem1.indexInResponse = item2.indexInResponse
                val newItem2 = item2.copy()
                newItem2.indexInResponse = item1.indexInResponse
                db.LoadMoreDao().update(newItem1)
                db.LoadMoreDao().update(newItem2)
            }
        }
    }

}