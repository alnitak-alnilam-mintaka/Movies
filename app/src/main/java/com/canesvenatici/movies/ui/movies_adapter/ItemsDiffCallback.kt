package com.canesvenatici.movies.ui.movies_adapter

import androidx.recyclerview.widget.DiffUtil
import com.canesvenatici.movies.ui.models.MovieUIModel

class ItemDiffCallback : DiffUtil.ItemCallback<MovieUIModel>() {

    override fun areItemsTheSame(oldItem: MovieUIModel, newItem: MovieUIModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: MovieUIModel, newItem: MovieUIModel): Boolean {
        return oldItem == newItem
    }
}