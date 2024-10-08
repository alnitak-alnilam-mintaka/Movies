package com.canesvenatici.movies.ui.features.home.favorites_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.canesvenatici.movies.R
import com.canesvenatici.movies.ui.models.FavoriteMovieUIModel

class FavoriteMoviesAdapter(
    private val onMovieClick: (Int) -> Unit,
    private val onSeeAllClick: () -> Unit
) : ListAdapter<FavoriteMovieUIModel, RecyclerView.ViewHolder>(ItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_TYPE_MOVIE) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favorite, parent, false)
            FavoriteMovieViewHolder(view, onMovieClick)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_see_all, parent, false)
            SeeAllViewHolder(view, onSeeAllClick)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FavoriteMovieViewHolder) {
            holder.bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < currentList.size) ITEM_TYPE_MOVIE else ITEM_TYPE_SEE_ALL
    }

    override fun getItemCount(): Int {
        return currentList.size + 1 // plus "see all" button
    }

    companion object {
        private const val ITEM_TYPE_MOVIE = 0
        private const val ITEM_TYPE_SEE_ALL = 1
    }
}
