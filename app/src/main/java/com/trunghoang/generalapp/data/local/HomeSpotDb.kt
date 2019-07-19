package com.trunghoang.generalapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.miller.loadmoredbnetwork.BaseLoadMoreDb
import com.trunghoang.generalapp.data.model.HomeSpot

/**
 * Created by Hoang Trung on 18/07/2019
 */

@Database(
    entities = [HomeSpot::class],
    version = 3,
    exportSchema = false
)
abstract class HomeSpotDb : BaseLoadMoreDb<HomeSpot, Int>() {
    companion object {
        private var INSTANCE: HomeSpotDb? = null

        fun create(context: Context): HomeSpotDb? {
            if (INSTANCE == null) {
                synchronized(HomeSpotDb::class.java) {
                    INSTANCE =
                        Room.databaseBuilder(
                            context.applicationContext,
                            HomeSpotDb::class.java,
                            "Homespot"
                        ).fallbackToDestructiveMigration()
                            .build()
                }
            }
            return INSTANCE
        }
    }

    abstract fun HomeSpotDao(): HomeSpotDao
}