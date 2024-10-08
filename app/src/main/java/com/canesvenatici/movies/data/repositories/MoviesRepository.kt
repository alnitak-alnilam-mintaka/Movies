package com.canesvenatici.movies.data.repositories

import com.canesvenatici.movies.data.database.api.dto.MovieEntity
import com.canesvenatici.movies.data.models.MovieModel
import com.canesvenatici.movies.ui.models.MovieDetailsUIModel
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    fun getAllMovies(): Flow<List<MovieEntity>>
    fun getStaffPicks(): Flow<List<MovieEntity>>
    fun getFavorites(): Flow<List<MovieEntity>>
    fun getMovie(modieId: Int): MovieEntity?
    fun toggleFavoriteStatus(movieId: Int)
    fun search(query: String, isFavorite: Boolean): Flow<List<MovieEntity>>
}