package com.canesvenatici.movies.ui.models

data class MovieDetailsUIModel(
    val id             : Int,
    val titleAndYear   : String,
    val rating         : Float,
    val revenue        : String,
    val dateAndRuntime : String,
    val posterUrl      : String,
    val overview       : String,
    val reviews        : String,
    val budget         : String,
    val language       : String,
    val genres         : List<String>,
    val isFavorite     : Boolean
)
