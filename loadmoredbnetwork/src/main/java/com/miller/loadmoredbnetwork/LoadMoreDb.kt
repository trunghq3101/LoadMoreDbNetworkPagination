package com.miller.loadmoredbnetwork

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Created by Hoang Trung on 18/07/2019
 */

@Database(
    entities = [BaseLoadMoreEntity::class],
    version = 3,
    exportSchema = false
)
abstract class LoadMoreDb: RoomDatabase() {
    companion object {
        private var INSTANCE: LoadMoreDb? = null

        fun create(context: Context): LoadMoreDb? {
            if (INSTANCE == null) {
                synchronized(LoadMoreDb::class.java) {
                    INSTANCE =
                        Room.databaseBuilder(
                            context.applicationContext,
                            LoadMoreDb::class.java,
                            "LoadMore"
                        ).fallbackToDestructiveMigration()
                            .build()
                }
            }
            return INSTANCE
        }
    }

    abstract fun LoadMoreDao(): LoadMoreDao<BaseLoadMoreEntity.Data>
}