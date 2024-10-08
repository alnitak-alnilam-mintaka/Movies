package com.canesvenatici.movies.ui.features.home.favorites_adapter

import androidx.recyclerview.widget.DiffUtil
import com.canesvenatici.movies.ui.models.FavoriteMovieUIModel

class ItemDiffCallback : DiffUtil.ItemCallback<FavoriteMovieUIModel>() {

    override fun areItemsTheSame(oldItem: FavoriteMovieUIModel, newItem: FavoriteMovieUIModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FavoriteMovieUIModel, newItem: FavoriteMovieUIModel): Boolean {
        return oldItem == newItem
    }
}