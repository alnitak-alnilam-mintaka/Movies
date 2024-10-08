package com.canesvenatici.movies.data.database.api.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "MOVIES")
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val posterUrl: String?,
    val overview: String?,
    val releaseDate: Date?,
    val runtime: Int?,
    val reviews: Int?,
    val budget: Int?,
    val language: String?,
    val genres: String?,
    val revenue: Int?,
    val rating: Float?,
    val isFavorite: Boolean,
    val isStaffPick: Boolean
)
