package com.canesvenatici.movies.ui.models

data class MovieUIModel(
    val id          : Int,
    val title       : String,
    val posterUrl   : String,
    val releaseDate : String,
    val rating      : Float,
    val isFavorite  : Boolean
)
