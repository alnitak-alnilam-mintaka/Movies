package com.canesvenatici.movies.ui.features.search

import com.canesvenatici.movies.ui.models.MovieUIModel

sealed interface SearchUIState

data object Loading : SearchUIState

data class ShowMovies(
    val movies: List<MovieUIModel>
) : SearchUIState

data class SearchError(val message: String?) : SearchUIState