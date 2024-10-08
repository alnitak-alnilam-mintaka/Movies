package com.canesvenatici.movies.mock

import com.canesvenatici.movies.data.database.api.dto.MovieEntity
import com.canesvenatici.movies.data.models.MovieModel
import java.util.Date

object MockData {

    val movie = MovieEntity(
        id = 1,
        title = "Inception",
        posterUrl = "https://example.com/inception.jpg",
        overview = "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.",
        releaseDate = Date(1279411200000),
        runtime = 148,
        reviews = 1200,
        budget = 160000000,
        language = "English",
        genres = "Action, Science Fiction, Thriller",
        revenue = 829895144,
        rating = 8.8f,
        isFavorite = false,
        isStaffPick = false
    )

    val favoriteStaffPickMovie = MovieEntity(
        id = 2,
        title = "Akira",
        posterUrl = "https://example.com/akira.jpg",
        overview = "A secret military project endangers Neo-Tokyo when it turns a biker gang member into a rampaging psychic psychopath that only two teenagers and a group of psychics can stop.",
        releaseDate = Date(592704000000),
        runtime = 124,
        reviews = 500,
        budget = 10000000,
        language = "Japanese",
        genres = "Animation, Science Fiction",
        revenue = 50000000,
        rating = 8.1f,
        isFavorite = true,
        isStaffPick = true
    )

    val allMovies = listOf(movie, favoriteStaffPickMovie)

    val mockMovieModel1 = MovieModel(
        rating = 8.5,
        id = 1,
        revenue = 100000000,
        releaseDate = Date(1279411200000),
        posterUrl = "https://example.com/poster1.jpg",
        runtime = 148,
        title = "Inception",
        overview = "A skilled thief is given a chance at redemption if he can successfully perform an inception.",
        reviews = 5000,
        budget = 160000000,
        language = "English",
        genres = listOf("Action", "Science Fiction", "Thriller")
    )

    val mockMovieModel2 = MovieModel(
        rating = 8.1,
        id = 2,
        revenue = 50000000,
        releaseDate = Date(592704000000),
        posterUrl = "https://example.com/poster2.jpg",
        runtime = 124,
        title = "Akira",
        overview = "In Neo-Tokyo, a secret military project turns a biker gang member into a rampaging psychic.",
        reviews = 3000,
        budget = 10000000,
        language = "Japanese",
        genres = listOf("Animation", "Science Fiction")
    )

    val allParsedMovies = listOf(mockMovieModel1, mockMovieModel2)
}