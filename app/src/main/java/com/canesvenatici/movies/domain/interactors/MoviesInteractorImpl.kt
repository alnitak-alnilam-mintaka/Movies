package com.canesvenatici.movies.domain.interactors

import com.canesvenatici.movies.data.database.api.dto.MovieEntity
import com.canesvenatici.movies.data.repositories.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class MoviesInteractorImpl(
    private val repository: MoviesRepository
): MoviesInteractor {

    override fun getAllMovies(): Flow<List<MovieEntity>> = repository.getAllMovies()

    override fun getFavoriteMovies(): Flow<List<MovieEntity>> = repository.getFavorites()

    override fun getFavoriteMoviesForHome(): Flow<List<MovieEntity>> = flow {
        repository.getFavorites()
            .collect { favoriteMovies ->
                emit(favoriteMovies.take(3))
            }
    }

    override fun getStaffPicks(): Flow<List<MovieEntity>> = repository.getStaffPicks()

    override fun getMovie(movieId: Int): Flow<MovieEntity?> = flow {
        emit(repository.getMovie(movieId))
    }

    override fun toggleFavoriteStatus(movieId: Int) =
        repository.toggleFavoriteStatus(movieId)

    override fun search(query: String, isFavorite: Boolean) = flow {
        when {
            query.isEmpty() && isFavorite -> emitAll(repository.getFavorites())
            query.isEmpty() && !isFavorite -> emitAll(repository.getAllMovies())
            query.isNotBlank() -> emitAll(repository.search(query, isFavorite))
        }
    }
}