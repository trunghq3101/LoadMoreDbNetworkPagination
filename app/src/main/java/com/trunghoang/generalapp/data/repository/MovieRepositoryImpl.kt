package com.trunghoang.generalapp.data.repository

import com.miller.responsivepaging.BaseLoadMoreWithDbRepository
import com.miller.responsivepaging.LoadMoreDb
import com.trunghoang.generalapp.data.model.Movie
import com.trunghoang.generalapp.data.model.MovieListResponse
import com.trunghoang.generalapp.data.remote.ApiService
import retrofit2.Call
import java.util.concurrent.Executor

/**
 * Created by Hoang Trung on 18/07/2019
 */

class MovieRepositoryImpl(
    ioExecutor: Executor,
    db: LoadMoreDb<Movie>,
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
}