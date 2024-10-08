package com.canesvenatici.movies.ui.features.home.favorites_adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SeeAllViewHolder(itemView: View, onSeeAllClick: () -> Unit) : RecyclerView.ViewHolder(itemView) {

    init {
        itemView.setOnClickListener {
            onSeeAllClick()
        }
    }
}