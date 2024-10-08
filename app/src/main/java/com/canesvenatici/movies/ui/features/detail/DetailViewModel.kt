package com.canesvenatici.movies.ui.features.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.canesvenatici.movies.domain.interactors.MoviesInteractor
import com.canesvenatici.movies.ui.mappers.toDetailsUIModel
import com.canesvenatici.movies.ui.models.MovieDetailsUIModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DetailViewModel(
    private val interactor: MoviesInteractor,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private var movie: MovieDetailsUIModel? = null

    private val viewModelUIState = MutableStateFlow<DetailsUIState>(Loading)
    val uiState : StateFlow<DetailsUIState> = viewModelUIState

    fun getMovie(movieId: Int) {
        viewModelScope.launch(dispatcher) {
            interactor.getMovie(movieId)
                .onStart {
                    viewModelUIState.value = Loading
                }
                .catch {
                    viewModelUIState.value = DetailError(message = it.message)
                }
                .collect { movie ->
                    if (movie != null) {
                        this@DetailViewModel.movie = movie.toDetailsUIModel()
                        viewModelUIState.value = Details(movie.toDetailsUIModel())
                    } else {
                        viewModelUIState.value = DetailError(message = "")
                    }
                }
        }
    }

    fun onFavoriteClick(movieId: Int, isChecked: Boolean) {
        if (isChecked != movie?.isFavorite) {
            viewModelScope.launch(dispatcher) {
                interactor.toggleFavoriteStatus(movieId)
            }
        }
    }
}