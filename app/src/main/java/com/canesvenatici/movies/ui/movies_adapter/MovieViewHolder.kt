package com.canesvenatici.movies.ui.movies_adapter

import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.canesvenatici.movies.R
import com.canesvenatici.movies.ui.models.MovieUIModel

class MovieViewHolder(
    itemView: View,
    private val onClick: (Int) -> Unit,
    private val onFavoriteClick: (Int, Boolean, Boolean) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    private val posterImageView = itemView.findViewById<ImageView>(R.id.poster_iv)
    private val titleTextView = itemView.findViewById<TextView>(R.id.title_tv)
    private val yearTextView = itemView.findViewById<TextView>(R.id.year_tv)
    private val ratingBar = itemView.findViewById<RatingBar>(R.id.rating_bar)
    private val favoriteCheckBox = itemView.findViewById<CheckBox>(R.id.favorite_cb)

    fun bind(movie: MovieUIModel) {
        favoriteCheckBox.buttonTintList = null
        with(movie) {
            titleTextView.text = title
            yearTextView.text = releaseDate
            ratingBar.rating = rating
            favoriteCheckBox.isChecked = isFavorite
            Glide
                .with(itemView.context)
                .load(movie.posterUrl)
                .into(posterImageView)
        }

        itemView.setOnClickListener {
            onClick(movie.id)
        }
        favoriteCheckBox.setOnCheckedChangeListener { _, isChecked ->
            onFavoriteClick(movie.id, movie.isFavorite, isChecked)
        }
    }
}
