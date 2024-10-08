package com.canesvenatici.movies.ui.features.search

import com.canesvenatici.movies.MainDispatcherRule
import com.canesvenatici.movies.domain.interactors.MoviesInteractor
import com.canesvenatici.movies.mock.MockData
import com.canesvenatici.movies.ui.mappers.toUIModels
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

class SearchViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val interactor: MoviesInteractor = mock()

    private lateinit var viewModel: SearchViewModel

    @Before
    fun before() {
        viewModel = SearchViewModel(interactor, mainDispatcherRule.testDispatcher)
    }

    @After
    fun after() {
        verifyNoMoreInteractions(interactor)
    }

    @Test
    fun `getMovies should load movies and update view state when movies are available`() = runTest {
        // Given
        val movies = MockData.allMovies
        whenever(interactor.search("Akira", false)).thenReturn(flowOf(movies))

        // When
        viewModel.getMovies("Akira", false)

        // Then
        verify(interactor).search("Akira", false)
        assertEquals(
            ShowMovies(MockData.allMovies.toUIModels()),
            viewModel.viewState.value
        )
    }

    @Test
    fun `getMovies should show error state when exception is thrown`() = runTest {
        // Given
        val errorMessage = "Network Error"
        whenever(interactor.search(any(), eq(false))).thenReturn(flow { throw RuntimeException(errorMessage) })

        // When
        viewModel.getMovies("Akira", false)

        // Then
        verify(interactor).search("Akira", false)
        assertEquals(
            SearchError(errorMessage),
            viewModel.viewState.value
        )
    }

    @Test
    fun `getMovies should set title state to FAVORITE when favorites flag is true`() = runTest {
        // Given
        whenever(interactor.search(any(), eq(true))).thenReturn(flowOf(emptyList()))

        // When
        viewModel.getMovies(null, true)

        // Then
        verify(interactor).search("", true)
        assertEquals(
            Title.FAVORITE,
            viewModel.titleState.value
        )
    }

    @Test
    fun `getMovies should set title state to ALL when favorites flag is false`() = runTest {
        // Given
        whenever(interactor.search(any(), eq(false))).thenReturn(flowOf(emptyList()))

        // When
        viewModel.getMovies(null, false)

        // Then
        verify(interactor).search("", false)
        assertEquals(
            Title.ALL,
            viewModel.titleState.value
        )
    }

    @Test
    fun `onFavoriteClick should call toggleFavoriteStatus when oldValue is not equal to newValue`() = runTest {
        // When
        viewModel.onFavoriteClick(1, oldValue = false, newValue = true)

        // Then
        verify(interactor).toggleFavoriteStatus(1)
    }

    @Test
    fun `onFavoriteClick should not call toggleFavoriteStatus when oldValue is equal to newValue`() = runTest {
        // When
        viewModel.onFavoriteClick(1, oldValue = true, newValue = true)

        // Then
        verify(interactor, never()).toggleFavoriteStatus(any())
    }
}