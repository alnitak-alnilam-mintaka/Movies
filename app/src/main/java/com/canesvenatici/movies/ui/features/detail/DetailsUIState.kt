package com.canesvenatici.movies.ui.features.detail

import com.canesvenatici.movies.ui.models.MovieDetailsUIModel

sealed interface DetailsUIState

data object Loading : DetailsUIState

data class Details(
    val movie: MovieDetailsUIModel
) : DetailsUIState

data class DetailError(val message: String?) : DetailsUIState