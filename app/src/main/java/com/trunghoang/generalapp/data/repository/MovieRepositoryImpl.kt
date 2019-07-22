package com.trunghoang.generalapp.data.repository

import com.miller.loadmoredbnetwork.BaseLoadMoreWithDbRepository
import com.miller.loadmoredbnetwork.LoadMoreDb
import com.trunghoang.generalapp.data.local.MovieRoomDao
import com.trunghoang.generalapp.data.model.Movie
import com.trunghoang.generalapp.data.model.MovieListResponse
import com.trunghoang.generalapp.data.remote.ApiService
import retrofit2.Call
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Created by Hoang Trung on 18/07/2019
 */

class MovieRepositoryImpl(
    private val ioExecutor: Executor,
    private val db: LoadMoreDb<Movie>,
    private val apiService: ApiService
) : BaseLoadMoreWithDbRepository<Movie, Int, MovieListResponse>(
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
        return apiService.searchMovies(keyword = "Avenger", page = key)
    }

    override fun swapItem(from: Movie, to: Movie) {
        Executors.newSingleThreadExecutor().execute {
            val start = from.indexInResponse
            val end = to.indexInResponse

            val items = (db.LoadMoreDao() as MovieRoomDao).getBetween(start, end)
            if (start < end) {
                for (i in 0 until end - start) {
                    Collections.swap(items, i, i+1)
                }
            } else {
                for (i in end - start downTo 1) {
                    Collections.swap(items, i, i - 1)
                }
            }
            (db.LoadMoreDao() as MovieRoomDao).updateAll(items)
        }
    }

}