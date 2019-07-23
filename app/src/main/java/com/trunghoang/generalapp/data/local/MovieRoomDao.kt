package com.trunghoang.generalapp.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.miller.loadmoredbnetwork.LoadMoreDao
import com.trunghoang.generalapp.data.model.Movie

/**
 * Created by Hoang Trung on 22/07/2019
 */

@Dao
abstract class MovieRoomDao : LoadMoreDao<Movie>
