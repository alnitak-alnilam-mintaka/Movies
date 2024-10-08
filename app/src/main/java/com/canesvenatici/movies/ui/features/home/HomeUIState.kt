package com.canesvenatici.movies.ui.features.home

import com.canesvenatici.movies.ui.models.FavoriteMovieUIModel
import com.canesvenatici.movies.ui.models.MovieUIModel

sealed interface HomeUIState

data object Loading : HomeUIState

data class ShowFavoriteMovies(
    val movies: List<FavoriteMovieUIModel>
) : HomeUIState

data class ShowStaffPicks(
    val movies: List<MovieUIModel>
) : HomeUIState

data class HomeError(val message: String?) : HomeUIState