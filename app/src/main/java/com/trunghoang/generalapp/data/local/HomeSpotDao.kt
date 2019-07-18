package com.trunghoang.generalapp.data.local

import androidx.paging.DataSource
import androidx.room.*
import com.trunghoang.generalapp.data.model.HomeSpot

/**
 * Created by Hoang Trung on 18/07/2019
 */

@Dao
interface HomeSpotDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(spots: List<HomeSpot>)

    @Query("SELECT * FROM spots ORDER BY indexInResponse ASC")
    fun getSpots(): DataSource.Factory<Int, HomeSpot>

    @Query("SELECT MAX(indexInResponse) + 1 FROM spots")
    fun getNextIndex(): Int

    @Query("SELECT * FROM spots WHERE indexInResponse =:position")
    fun getSpot(position: Int): HomeSpot

    @Update
    fun updateSpot(spot: HomeSpot)
}