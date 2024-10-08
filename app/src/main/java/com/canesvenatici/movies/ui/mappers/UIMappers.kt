package com.canesvenatici.movies.ui.mappers

import android.icu.util.Calendar
import com.canesvenatici.movies.data.database.api.dto.MovieEntity
import com.canesvenatici.movies.ui.models.FavoriteMovieUIModel
import com.canesvenatici.movies.ui.models.MovieDetailsUIModel
import com.canesvenatici.movies.ui.models.MovieUIModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun MovieEntity.toUIModel(): MovieUIModel {
    return MovieUIModel(
        id = this.id,
        title = this.title,
        posterUrl = this.posterUrl.orEmpty(),
        releaseDate = getYear(this.releaseDate),
        rating = this.rating ?: 0f,
        isFavorite = isFavorite
    )
}

private fun getYear(date: Date?): String {
    val calendar = Calendar.getInstance()
    calendar.time = date
    return calendar.get(Calendar.YEAR).toString()
}

fun List<MovieEntity>.toUIModels(): List<MovieUIModel> = this.map { it.toUIModel() }

fun MovieEntity.toDetailsUIModel(): MovieDetailsUIModel {
    return MovieDetailsUIModel(
        id = this.id,
        titleAndYear = "${this.title} (${getYear(this.releaseDate)})",
        posterUrl = this.posterUrl.orEmpty(),
        dateAndRuntime = "${formatDate(this.releaseDate)} · ${formatRuntime(runtime)}",
        rating = this.rating ?: 0f,
        budget = formatCurrency(budget ?: 0),
        revenue = formatCurrency(revenue ?: 0),
        genres = genres?.split(",")?.map { it.trim() } ?: emptyList(),
        language = Locale(language).displayLanguage,
        overview = overview.orEmpty(),
        reviews = "$rating ($reviews)",
        isFavorite = isFavorite
    )
}

fun MovieEntity.toFavoriteUIModel(): FavoriteMovieUIModel {
    return FavoriteMovieUIModel(
        id = this.id,
        posterUrl = this.posterUrl.orEmpty()
    )
}

fun List<MovieEntity>.toFavoriteUIModels(): List<FavoriteMovieUIModel> = this.map { it.toFavoriteUIModel() }

private fun formatCurrency(value: Int): String {
    val numberFormat = NumberFormat.getNumberInstance(Locale.GERMANY)
    return "$ ${numberFormat.format(value)}"
}

private fun formatDate(date: Date?): String {
    if (date != null) {
        val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return formatter.format(date)
    } else {
        return ""
    }
}

private fun formatRuntime(minutes: Int?): String {
    if (minutes != null) {
        val hours = minutes / 60
        val remainingMinutes = minutes % 60
        return "${hours}h ${remainingMinutes}m"
    } else {
        return "0h 0m"
    }
}
