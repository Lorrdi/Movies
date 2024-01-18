package com.example.movies

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    companion object {
        private lateinit var instance: MovieDatabase
        private const val DB_NAME = "movie.db"

        fun getInstance(application: Application): MovieDatabase {
            if (!::instance.isInitialized) instance = Room.databaseBuilder(
                application,
                MovieDatabase::class.java,
                DB_NAME
            ).build()

            return instance
        }
    }
    abstract fun movieDao(): MovieDao

}