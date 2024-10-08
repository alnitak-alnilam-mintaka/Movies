package com.canesvenatici.movies.data.database.api.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.canesvenatici.movies.data.database.api.dto.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovies(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStaffPicks(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: MovieEntity)

    @Query("SELECT * FROM MOVIES")
    fun getAllMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM MOVIES WHERE isStaffPick = 1")
    fun getStaffPicks(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM MOVIES WHERE isFavorite = 1")
    fun getFavoriteMovies(): Flow<List<MovieEntity>>

    @Query("UPDATE MOVIES SET isFavorite = NOT isFavorite WHERE id = :id")
    fun toggleFavoriteStatus(id: Int)

    @Query("SELECT * FROM MOVIES WHERE id = :id")
    fun getMovieById(id: Int): MovieEntity?

    @Query("SELECT * FROM MOVIES WHERE title LIKE '%' || :query || '%' AND isFavorite = :isFavorite")
    fun getMoviesByName(query: String, isFavorite: Int): Flow<List<MovieEntity>>
}