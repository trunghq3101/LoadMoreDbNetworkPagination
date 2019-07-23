package com.trunghoang.generalapp.data.repository

import com.miller.loadmoredbnetwork.ILoadMoreWithDbRepository
import com.trunghoang.generalapp.data.model.Movie
import com.trunghoang.generalapp.data.model.MovieListResponse

/**
 * Created by Hoang Trung on 19/07/2019
 */

interface MovieRepository: ILoadMoreWithDbRepository<Movie, Int, MovieListResponse>
