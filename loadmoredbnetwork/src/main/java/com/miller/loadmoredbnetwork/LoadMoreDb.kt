package com.miller.loadmoredbnetwork

import androidx.room.RoomDatabase

/**
 * Created by Hoang Trung on 18/07/2019
 */

abstract class LoadMoreDb<T: BaseLoadMoreEntity>: RoomDatabase() {
    abstract fun LoadMoreDao(): LoadMoreDao<T>
}