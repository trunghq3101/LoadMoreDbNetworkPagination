package com.trunghoang.generalapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import com.miller.responsivepaging.LoadMoreDb
import com.trunghoang.generalapp.data.model.Movie

/**
 * Created by Hoang Trung on 22/07/2019
 */

@Database(
    entities = [Movie::class],
    version = 1,
    exportSchema = false
)
abstract class MovieRoomDb : LoadMoreDb<Movie>() {
    companion object {
        private var INSTANCE: MovieRoomDb? = null

        fun create(context: Context): MovieRoomDb? {
            if (INSTANCE == null) {
                synchronized(MovieRoomDb::class.java) {
                    INSTANCE =
                        Room.databaseBuilder(
                            context.applicationContext,
                            MovieRoomDb::class.java,
                            "MovieRoomDb"
                        ).fallbackToDestructiveMigration()
                            .build()
                }
            }
            return INSTANCE
        }
    }

    abstract override fun LoadMoreDao(): MovieRoomDao
}