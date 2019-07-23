package com.trunghoang.generalapp.data.local

import androidx.room.Dao
import com.miller.responsivepaging.LoadMoreDao
import com.trunghoang.generalapp.data.model.Movie

/**
 * Created by Hoang Trung on 22/07/2019
 */

@Dao
abstract class MovieRoomDao : LoadMoreDao<Movie>
