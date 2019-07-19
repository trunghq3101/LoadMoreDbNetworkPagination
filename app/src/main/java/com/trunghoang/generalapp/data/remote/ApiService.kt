package com.trunghoang.generalapp.data.remote

import com.trunghoang.generalapp.data.model.HomeSpotListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Hoang Trung on 19/07/2019
 */

interface ApiService {

    @GET("")
    fun searchMovies(
        @Query("s") keyword: String? = null,
        @Query("page") page: Int? =null
    ): Call<HomeSpotListResponse>
}