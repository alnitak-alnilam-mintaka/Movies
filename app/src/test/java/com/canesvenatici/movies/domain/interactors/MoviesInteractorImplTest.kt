package com.canesvenatici.movies.domain.interactors

import com.canesvenatici.movies.data.repositories.MoviesRepository
import com.canesvenatici.movies.mock.MockData
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

class MoviesInteractorImplTest {

    private val repository: MoviesRepository = mock()
    private lateinit var interactor: MoviesInteractorImpl

    @Before
    fun setUp() {
        interactor = MoviesInteractorImpl(repository)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun `getAllMovies should call repository and return all movies`() = runTest {
        // Given
        val allMovies = MockData.allMovies
        whenever(repository.getAllMovies()).thenReturn(flowOf(allMovies))

        // When
        val result = interactor.getAllMovies().first()

        // Then
        assertEquals(allMovies, result)
        verify(repository).getAllMovies()
    }

    @Test
    fun `getFavoriteMovies should call repository and return favorite movies`() = runTest {
        // Given
        val favoriteMovies = listOf(MockData.favoriteStaffPickMovie)
        whenever(repository.getFavorites()).thenReturn(flowOf(favoriteMovies))

        // When
        val result = interactor.getFavoriteMovies().first()

        // Then
        assertEquals(favoriteMovies, result)
        verify(repository).getFavorites()
    }

    @Test
    fun `getFavoriteMoviesForHome should return only first 3 favorite movies`() = runTest {
        // Given
        val favoriteMovies = listOf(
            MockData.favoriteStaffPickMovie,
            MockData.favoriteStaffPickMovie,
            MockData.favoriteStaffPickMovie,
            MockData.favoriteStaffPickMovie,
            MockData.favoriteStaffPickMovie
        )
        whenever(repository.getFavorites()).thenReturn(flowOf(favoriteMovies))

        // When
        val result = interactor.getFavoriteMoviesForHome().first()

        // Then
        assertEquals(favoriteMovies.take(3), result)
        verify(repository).getFavorites()
    }

    @Test
    fun `getStaffPicks should call repository and return staff picks`() = runTest {
        // Given
        val staffPicks = listOf(MockData.favoriteStaffPickMovie)
        whenever(repository.getStaffPicks()).thenReturn(flowOf(staffPicks))

        // When
        val result = interactor.getStaffPicks().first()

        // Then
        assertEquals(staffPicks, result)
        verify(repository).getStaffPicks()
    }

    @Test
    fun `getMovie should call repository and return specific movie`() = runTest {
        // Given
        val movieId = 1
        val movie = MockData.movie
        whenever(repository.getMovie(movieId)).thenReturn(movie)

        // When
        val result = interactor.getMovie(movieId).first()

        // Then
        assertEquals(movie, result)
        verify(repository).getMovie(movieId)
    }

    @Test
    fun `toggleFavoriteStatus should call repository to toggle favorite status`() = runTest {
        // Given
        val movieId = 1

        // When
        interactor.toggleFavoriteStatus(movieId)

        // Then
        verify(repository).toggleFavoriteStatus(movieId)
    }

    @Test
    fun `search should emit favorite movies when query is empty and isFavorite is true`() = runTest {
        // Given
        val favoriteMovies = listOf(MockData.favoriteStaffPickMovie)
        whenever(repository.getFavorites()).thenReturn(flowOf(favoriteMovies))

        // When
        val result = interactor.search("", true).first()

        // Then
        assertEquals(favoriteMovies, result)
        verify(repository).getFavorites()
    }

    @Test
    fun `search should emit all movies when query is empty and isFavorite is false`() = runTest {
        // Given
        val allMovies = MockData.allMovies
        whenever(repository.getAllMovies()).thenReturn(flowOf(allMovies))

        // When
        val result = interactor.search("", false).first()

        // Then
        assertEquals(allMovies, result)
        verify(repository).getAllMovies()
    }

    @Test
    fun `search should call repository with query and isFavorite flag when query is not blank`() = runTest {
        // Given
        val query = "Akira"
        val isFavorite = true
        val searchResults = listOf(MockData.favoriteStaffPickMovie)
        whenever(repository.search(query, isFavorite)).thenReturn(flowOf(searchResults))

        // When
        val result = interactor.search(query, isFavorite).first()

        // Then
        assertEquals(searchResults, result)
        verify(repository).search(query, isFavorite)
    }
}