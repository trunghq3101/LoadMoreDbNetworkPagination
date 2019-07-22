package com.miller.loadmoredbnetwork

import android.graphics.Movie
import androidx.paging.DataSource
import androidx.room.*

/**
 * Created by Hoang Trung on 18/07/2019
 */

@Dao
interface LoadMoreDao<T: BaseLoadMoreEntity> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insert(list: List<T>)

    @Update
    fun update(item: T)

    @Update
    @JvmSuppressWildcards
    fun updateAll(items: List<T>)

    @Query("SELECT * FROM page_data ORDER BY indexInResponse ASC")
    fun getPagingData(): DataSource.Factory<Int, T>

    @Query("SELECT MAX(indexInResponse) + 1 FROM page_data")
    fun getNextIndex(): Int

    @Query("SELECT * FROM page_data WHERE indexInResponse = :position ")
    fun getItem(position: Int): T

    @Query("SELECT * FROM page_data WHERE indexInResponse BETWEEN :from AND :to")
    fun getBetween(from: Int, to: Int): List<T>
}