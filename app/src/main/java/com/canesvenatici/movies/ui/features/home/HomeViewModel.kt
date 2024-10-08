package com.canesvenatici.movies.ui.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.canesvenatici.movies.domain.interactors.MoviesInteractor
import com.canesvenatici.movies.ui.mappers.toFavoriteUIModels
import com.canesvenatici.movies.ui.mappers.toUIModels
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HomeViewModel(
    private val interactor: MoviesInteractor,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _staffPicks = MutableStateFlow<HomeUIState>(Loading)
    val staffPicks : StateFlow<HomeUIState> = _staffPicks

    private val _favorites = MutableStateFlow<HomeUIState>(Loading)
    val favorites : StateFlow<HomeUIState> = _favorites

    init {
        getMovies()
    }

    fun getMovies() {
        viewModelScope.launch(dispatcher) {
            interactor.getFavoriteMoviesForHome()
                .onStart {
                    _favorites.value = Loading
                }
                .catch {
                    _favorites.value = HomeError(message = it.message)
                }
                .collect { movies ->
                    _favorites.value =
                        if (movies.isNotEmpty()) ShowFavoriteMovies(movies.toFavoriteUIModels())
                        else HomeError("")
                }
        }
        viewModelScope.launch(Dispatchers.IO) {
            interactor.getStaffPicks()
                .onStart {
                    _staffPicks.value = Loading
                }
                .catch {
                    _staffPicks.value = HomeError(message = it.message)
                }
                .collectLatest { movies ->
                    _staffPicks.value =
                        if (movies.isNotEmpty()) ShowStaffPicks(movies.toUIModels())
                        else HomeError("")
                }
        }
    }

    fun onFavoriteClick(movieId: Int, oldValue: Boolean, newValue: Boolean) {
        if (oldValue != newValue) {
            viewModelScope.launch(Dispatchers.IO) {
                interactor.toggleFavoriteStatus(movieId)
            }
        }
    }
}