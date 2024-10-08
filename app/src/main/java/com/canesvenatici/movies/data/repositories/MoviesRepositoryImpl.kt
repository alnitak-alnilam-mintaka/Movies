package com.canesvenatici.movies.data.repositories

import com.canesvenatici.movies.data.JsonParser
import com.canesvenatici.movies.data.database.api.dao.MoviesDao
import com.canesvenatici.movies.data.database.api.dto.MovieEntity
import com.canesvenatici.movies.data.mappers.toEntities
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class MoviesRepositoryImpl(
    private val jsonParser: JsonParser,
    private val dao: MoviesDao,
): MoviesRepository {

    override fun getStaffPicks(): Flow<List<MovieEntity>>  = flow {
        val savedStaffPicks = dao.getStaffPicks().first()

        if (savedStaffPicks.isEmpty()) {
            val newStaffPicks = jsonParser.parseStaffPicks().toEntities(isStaffPick = true)
            dao.insertStaffPicks(newStaffPicks)
        }

        emitAll(dao.getStaffPicks())
    }

    override fun getAllMovies(): Flow<List<MovieEntity>> = flow {
        var allMovies = dao.getAllMovies().first()

        if (allMovies.none { !it.isStaffPick }) {
            allMovies = jsonParser.parseAllMovies().toEntities(isStaffPick = false)
            dao.insertMovies(allMovies)
        }

        emitAll(dao.getAllMovies())
    }

    override fun getFavorites(): Flow<List<MovieEntity>> {
        return dao.getFavoriteMovies()
    }

    override fun getMovie(modieId: Int): MovieEntity? =
        dao.getMovieById(modieId)

    override fun toggleFavoriteStatus(movieId: Int) =
        dao.toggleFavoriteStatus(movieId)

    override fun search(query: String, isFavorite: Boolean): Flow<List<MovieEntity>> =
        dao.getMoviesByName(query = query, isFavorite = if (isFavorite) 1 else 0)
}