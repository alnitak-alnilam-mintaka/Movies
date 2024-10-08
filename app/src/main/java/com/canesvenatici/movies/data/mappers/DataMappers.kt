package com.canesvenatici.movies.data.mappers

import com.canesvenatici.movies.data.database.api.dto.MovieEntity
import com.canesvenatici.movies.data.models.MovieModel

fun MovieModel.toEntity(isFavorite: Boolean = false, isStaffPick: Boolean = false): MovieEntity {
    return MovieEntity(
        id = this.id ?: 0,
        title = this.title.orEmpty(),
        posterUrl = this.posterUrl.orEmpty(),
        releaseDate = releaseDate,
        rating = this.rating?.toFloat() ?: 0f,
        budget = budget,
        genres = genres.joinToString(","),
        language = language,
        overview = overview,
        revenue = revenue,
        reviews = reviews,
        runtime = runtime,
        isStaffPick = isStaffPick,
        isFavorite = isFavorite
    )
}

fun List<MovieModel>.toEntities(isStaffPick: Boolean = false): List<MovieEntity> =
    this.map { it.toEntity(isFavorite = false, isStaffPick = isStaffPick) }
