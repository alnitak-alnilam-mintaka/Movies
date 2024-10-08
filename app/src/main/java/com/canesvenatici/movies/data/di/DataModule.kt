package com.canesvenatici.movies.data.di

import android.app.Application
import androidx.room.Room
import com.canesvenatici.movies.data.JsonParser
import com.canesvenatici.movies.data.repositories.MoviesRepository
import com.canesvenatici.movies.data.repositories.MoviesRepositoryImpl
import com.canesvenatici.movies.data.database.api.dao.MoviesDao
import com.canesvenatici.movies.data.database.impl.MoviesDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single { JsonParser(androidContext()) }
    single<MoviesRepository> { MoviesRepositoryImpl(get(), get()) }
    single { provideDataBase(get()) }
    single { provideDao(get()) }
}

fun provideDataBase(application: Application): MoviesDatabase =
    Room.databaseBuilder(
        application,
        MoviesDatabase::class.java,
        "movies_table"
    )
        .fallbackToDestructiveMigration()
        .build()

fun provideDao(moviesDatabase: MoviesDatabase): MoviesDao = moviesDatabase.getMoviesDao()