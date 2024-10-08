package com.canesvenatici.movies.ui.features.detail.adapter

import androidx.recyclerview.widget.DiffUtil
import com.canesvenatici.movies.ui.models.MovieUIModel

class ItemDiffCallback : DiffUtil.ItemCallback<String>() {

    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}