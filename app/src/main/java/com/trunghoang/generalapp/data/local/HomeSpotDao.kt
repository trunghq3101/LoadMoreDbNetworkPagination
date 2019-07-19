package com.trunghoang.generalapp.data.local

import androidx.paging.DataSource
import androidx.room.*
import com.miller.loadmoredbnetwork.BaseLoadMoreDb
import com.trunghoang.generalapp.data.model.HomeSpot

/**
 * Created by Hoang Trung on 18/07/2019
 */

@Dao
abstract class HomeSpotDao: BaseLoadMoreDb.ILoadMoreDao<HomeSpot, Int> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(list: List<HomeSpot>)

    @Query("SELECT * FROM page_data WHERE indexInResponse =:position")
    abstract fun getSpot(position: Int): HomeSpot

    @Update
    abstract fun updateSpot(spot: HomeSpot)
}