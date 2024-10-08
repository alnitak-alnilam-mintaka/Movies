package com.canesvenatici.movies.ui.features.detail.adapter

import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.canesvenatici.movies.R
import com.canesvenatici.movies.ui.models.MovieUIModel

class GenreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val titleTextView = itemView.findViewById<TextView>(R.id.genre_tv)

    fun bind(genre: String) {
        titleTextView.text = genre
    }
}
