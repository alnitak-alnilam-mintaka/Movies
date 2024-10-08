package com.canesvenatici.movies.domain.interactors

import com.canesvenatici.movies.data.database.api.dto.MovieEntity
import com.canesvenatici.movies.ui.models.FavoriteMovieUIModel
import com.canesvenatici.movies.ui.models.MovieDetailsUIModel
import com.canesvenatici.movies.ui.models.MovieUIModel
import kotlinx.coroutines.flow.Flow

interface MoviesInteractor {

    fun getAllMovies() : Flow<List<MovieEntity>>
    fun getFavoriteMovies() : Flow<List<MovieEntity>>
    fun getFavoriteMoviesForHome() : Flow<List<MovieEntity>>
    fun getStaffPicks() : Flow<List<MovieEntity>>
    fun getMovie(movieId: Int) : Flow<MovieEntity?>
    fun toggleFavoriteStatus(movieId: Int)
    fun search(query: String, isFavorite: Boolean): Flow<List<MovieEntity>>
}