package com.canesvenatici.movies.ui.features.home.favorites_adapter

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.canesvenatici.movies.R
import com.canesvenatici.movies.ui.models.FavoriteMovieUIModel

class FavoriteMovieViewHolder(
    itemView: View,
    private val onMovieClick: (Int) -> Unit,
) : RecyclerView.ViewHolder(itemView) {
    private val posterImageView = itemView.findViewById<ImageView>(R.id.poster_iv)

    fun bind(movie: FavoriteMovieUIModel) {
        Glide
            .with(itemView.context)
            .load(movie.posterUrl)
            .into(posterImageView)
        itemView.setOnClickListener {
            onMovieClick(movie.id)
        }
    }
}
