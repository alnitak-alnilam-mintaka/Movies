package com.canesvenatici.movies.data.repositories

import com.canesvenatici.movies.data.JsonParser
import com.canesvenatici.movies.data.database.api.dao.MoviesDao
import com.canesvenatici.movies.data.mappers.toEntities
import com.canesvenatici.movies.mock.MockData
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

class MoviesRepositoryImplTest {

    private val jsonParser: JsonParser = mock(JsonParser::class.java)
    private val dao: MoviesDao = mock(MoviesDao::class.java)
    private lateinit var repository: MoviesRepositoryImpl

    @Before
    fun setUp() {
        repository = MoviesRepositoryImpl(jsonParser, dao)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(dao, jsonParser)
    }

    @Test
    fun `getStaffPicks should return staff picks from DAO if they exist`() = runTest {
        // Given
        val staffPicks = MockData.allMovies
        whenever(dao.getStaffPicks()).thenReturn(flowOf(staffPicks))

        // When
        val result = repository.getStaffPicks().first()

        // Then
        assertEquals(staffPicks, result)
        verify(dao, times(2)).getStaffPicks()
    }

    @Test
    fun `getStaffPicks should parse and insert new staff picks if none exist`() = runTest {
        // Given
        whenever(dao.getStaffPicks()).thenReturn(flowOf(emptyList()))
        val newStaffPicks = MockData.allParsedMovies
        whenever(jsonParser.parseStaffPicks()).thenReturn(newStaffPicks)

        // When
        repository.getStaffPicks().first()

        // Then
        verify(jsonParser).parseStaffPicks()
        verify(dao).insertStaffPicks(newStaffPicks.toEntities(false))
        verify(dao, times(2)).getStaffPicks()
    }

    @Test
    fun `getAllMovies should return all movies from DAO if they exist`() = runTest {
        // Given
        val allMovies = MockData.allMovies
        whenever(dao.getAllMovies()).thenReturn(flowOf(allMovies))

        // When
        val result = repository.getAllMovies().first()

        // Then
        assertEquals(allMovies, result)
        verify(dao, times(2)).getAllMovies()
    }

    @Test
    fun `getAllMovies should parse and insert new movies if no non-staff picks exist`() = runTest {
        // Given
        val newMovies = MockData.allParsedMovies
        whenever(dao.getAllMovies()).thenReturn(flowOf(listOf(MockData.favoriteStaffPickMovie)))
        whenever(jsonParser.parseAllMovies()).thenReturn(newMovies)

        // When
        repository.getAllMovies().first()

        // Then
        verify(jsonParser).parseAllMovies()
        verify(dao).insertMovies(newMovies.toEntities(false))
        verify(dao, times(2)).getAllMovies()
    }

    @Test
    fun `getFavorites should return favorite movies`() = runTest {
        // Given
        val favoriteMovies = listOf(MockData.favoriteStaffPickMovie)
        whenever(dao.getFavoriteMovies()).thenReturn(flowOf(favoriteMovies))

        // When
        val result = repository.getFavorites().first()

        // Then
        assertEquals(favoriteMovies, result)
        verify(dao).getFavoriteMovies()
    }

    @Test
    fun `getMovie should return a movie by its ID`() {
        // Given
        val movieId = 1
        val movie = MockData.movie
        whenever(dao.getMovieById(movieId)).thenReturn(movie)

        // When
        val result = repository.getMovie(movieId)

        // Then
        assertEquals(movie, result)
        verify(dao).getMovieById(movieId)
    }

    @Test
    fun `toggleFavoriteStatus should call DAO to toggle favorite status`() = runTest {
        // Given
        val movieId = 1

        // When
        repository.toggleFavoriteStatus(movieId)

        // Then
        verify(dao).toggleFavoriteStatus(movieId)
    }

    @Test
    fun `search should call DAO with correct query`() = runTest {
        // Given
        val query = "Akira"
        val isFavorite = false
        val expectedResult = listOf(MockData.favoriteStaffPickMovie)
        whenever(dao.getMoviesByName(query, 1)).thenReturn(flowOf(expectedResult))

        // When
        val result = repository.search(query, isFavorite).first()

        // Then
        assertEquals(expectedResult, result)
        verify(dao).getMoviesByName(query, 1)
    }

    @Test
    fun `search should call DAO with correct query and filter favorite movies`() = runTest {
        // Given
        val query = "Akira"
        val isFavorite = true
        val expectedResult = listOf(MockData.favoriteStaffPickMovie)
        whenever(dao.getMoviesByName(query, 0)).thenReturn(flowOf(expectedResult))

        // When
        val result = repository.search(query, isFavorite).first()

        // Then
        assertEquals(expectedResult, result)
        verify(dao).getMoviesByName(query, 0)
    }
}