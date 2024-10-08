package com.canesvenatici.movies.data.database.impl

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.canesvenatici.movies.data.database.api.dao.MoviesDao
import com.canesvenatici.movies.data.database.api.dto.MovieEntity


@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun getMoviesDao(): MoviesDao
}