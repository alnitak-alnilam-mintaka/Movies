package com.canesvenatici.movies.ui.movies_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.canesvenatici.movies.R
import com.canesvenatici.movies.ui.models.MovieUIModel

class MoviesAdapter(
    private val onClick: (Int) -> Unit,
    private val onFavoriteClick: (Int, Boolean, Boolean) -> Unit,
) : ListAdapter<MovieUIModel, MovieViewHolder>(ItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view, onClick, onFavoriteClick)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
