package com.canesvenatici.movies.ui.features.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.canesvenatici.movies.domain.interactors.MoviesInteractor
import com.canesvenatici.movies.ui.mappers.toUIModels
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class SearchViewModel(
    private val interactor: MoviesInteractor,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val viewModelViewState = MutableStateFlow<SearchUIState>(Loading)
    val viewState : StateFlow<SearchUIState> = viewModelViewState

    private val _titleState = MutableStateFlow(Title.ALL)
    val titleState : StateFlow<Title> = _titleState

    fun getMovies(query: String?, favorites: Boolean) {
        viewModelScope.launch(dispatcher) {
            _titleState.value = if (favorites) Title.FAVORITE else Title.ALL
            interactor.search(query.orEmpty(), favorites)
                .onStart {
                    viewModelViewState.value = Loading
                }
                .catch {
                    viewModelViewState.value = SearchError(it.message)
                }
                .collect { movies ->
                    viewModelViewState.value =
                        if (movies.isNotEmpty()) ShowMovies(movies.toUIModels())
                        else SearchError("")
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