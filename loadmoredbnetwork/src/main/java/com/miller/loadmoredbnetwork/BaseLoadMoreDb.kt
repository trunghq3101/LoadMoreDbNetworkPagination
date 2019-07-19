package com.miller.loadmoredbnetwork

import androidx.paging.DataSource
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RoomDatabase

/**
 * Created by Hoang Trung on 19/07/2019
 */

abstract class BaseLoadMoreDb<Item: ILoadMoreEntity, Key>: RoomDatabase() {
    abstract fun Dao(): ILoadMoreDao<Item, Key>

    interface ILoadMoreDao<Item: ILoadMoreEntity, Key> {
        @Query("SELECT * FROM page_data ORDER BY indexInResponse ASC")
        fun getPagingData(): DataSource.Factory<Key, Item>
        @Query("SELECT MAX(indexInResponse) + 1 FROM page_data")
        fun getNextIndex(): Int
    }
}