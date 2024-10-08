package com.canesvenatici.movies.ui.features.search

import androidx.annotation.StringRes
import com.canesvenatici.movies.R

enum class Title(@StringRes val stringRes: Int) {
    ALL(R.string.all_title),
    FAVORITE(R.string.favorite_title)
}