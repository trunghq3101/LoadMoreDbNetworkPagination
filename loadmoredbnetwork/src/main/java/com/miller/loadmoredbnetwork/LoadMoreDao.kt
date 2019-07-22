package com.miller.loadmoredbnetwork

import androidx.paging.DataSource
import androidx.room.*

/**
 * Created by Hoang Trung on 18/07/2019
 */

@Dao
abstract class LoadMoreDao<T: BaseLoadMoreEntity.Data> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(list: List<BaseLoadMoreEntity<T>>)

    @Update
    abstract fun update(item: BaseLoadMoreEntity<T>)

    @Query("SELECT * FROM page_data ORDER BY indexInResponse ASC")
    abstract fun getPagingData(): DataSource.Factory<String, BaseLoadMoreEntity<T>>

    @Query("SELECT MAX(indexInResponse) + 1 FROM page_data")
    abstract fun getNextIndex(): Int

    @Query("SELECT * FROM page_data WHERE indexInResponse = :position ")
    abstract fun getItem(position: Int): BaseLoadMoreEntity<T>
}