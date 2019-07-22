package com.trunghoang.generalapp.data.repository

import android.util.Log
import com.miller.loadmoredbnetwork.BaseLoadMoreWithDbRepository
import com.miller.loadmoredbnetwork.LoadMoreDb
import com.trunghoang.generalapp.data.local.MovieRoomDao
import com.trunghoang.generalapp.data.model.Movie
import com.trunghoang.generalapp.data.model.MovieListResponse
import com.trunghoang.generalapp.data.remote.ApiService
import retrofit2.Call
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import kotlin.math.max
import kotlin.math.min

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
            val t = from.indexInResponse
            from.indexInResponse = to.indexInResponse
            Log.d("SWAPPPPP", ": $t --- ${to.indexInResponse}")
            to.indexInResponse = t
            db.LoadMoreDao().update(from)
            db.LoadMoreDao().update(to)
            /*db.runInTransaction {

                val t = from.indexInResponse
                from.indexInResponse = to.indexInResponse
                to.indexInResponse = t
                db.LoadMoreDao().update(from)
                db.LoadMoreDao().update(to)

                *//*val start = min(from.indexInResponse, to.indexInResponse)
                val end = max(from.indexInResponse, to.indexInResponse)
                val items = (db.LoadMoreDao() as MovieRoomDao).getBetween(start, end)
                val last = items.lastIndex
                var prev = items[0].indexInResponse
                var next: Int
                for (i in 1 until items.size) {
                    next = items[i].indexInResponse
                    items[i].indexInResponse = prev
                    prev = next
                }
                items[0].indexInResponse = last
                (db.LoadMoreDao() as MovieRoomDao).updateAll(items)*//*
            }*/
            /*val t = from.indexInResponse
            from.indexInResponse = to.indexInResponse
            to.indexInResponse = t
            db.LoadMoreDao().update(from)
            db.LoadMoreDao().update(to)*/
        }
    }

}