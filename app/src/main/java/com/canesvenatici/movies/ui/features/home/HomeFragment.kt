package com.canesvenatici.movies.ui.features.home

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.widget.ContentLoadingProgressBar
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.canesvenatici.movies.R
import com.canesvenatici.movies.ui.movies_adapter.MoviesAdapter
import com.canesvenatici.movies.ui.features.home.favorites_adapter.FavoriteMoviesAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel<HomeViewModel>()

    private lateinit var favoritesAdapter: FavoriteMoviesAdapter
    private lateinit var staffPicksAdapter: MoviesAdapter

    private lateinit var favoritesLoadingBar : ContentLoadingProgressBar
    private lateinit var staffPicksLoadingBar : ContentLoadingProgressBar

    private lateinit var favoritesRecyclerView : RecyclerView
    private lateinit var staffPicksRecyclerView : RecyclerView

    private lateinit var noStaffPicksMessage : TextView
    private lateinit var noFavoritesMessage : TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI(view)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favorites.collectLatest { state ->
                    updateFavoritesState(state)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.staffPicks.collectLatest { state ->
                    updateStaffPicksState(state)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getMovies()
    }

    private fun initUI(view: View) {
        initFavoritesRecyclerView(view)
        initStaffPicksRecyclerView(view)
        favoritesLoadingBar = view.findViewById(R.id.favorites_pb)
        staffPicksLoadingBar = view.findViewById(R.id.staff_picks_pb)
        view.findViewById<CardView>(R.id.search_btn).setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToSearchFragment()
            )
        }

        noStaffPicksMessage = view.findViewById(R.id.no_staff_picks_tv)
        noFavoritesMessage = view.findViewById(R.id.no_favorites_tv)

        view.findViewById<TextView>(R.id.favorites_title).text =
            Html.fromHtml(getString(R.string.your_favorites_title), Html.FROM_HTML_MODE_LEGACY)
        view.findViewById<TextView>(R.id.staff_picks_title).text =
            Html.fromHtml(getString(R.string.staff_picks_title), Html.FROM_HTML_MODE_LEGACY)
    }

    private fun updateStaffPicksState(state: HomeUIState) {
        when (state) {
            Loading -> {
                staffPicksLoadingBar.show()
                staffPicksRecyclerView.visibility = View.INVISIBLE
                noStaffPicksMessage.visibility = View.GONE
            }
            is ShowStaffPicks -> {
                staffPicksLoadingBar.hide()
                staffPicksAdapter.submitList(state.movies)
                staffPicksRecyclerView.visibility = View.VISIBLE
                noStaffPicksMessage.visibility = View.GONE
            }
            else -> {
                staffPicksLoadingBar.hide()
                noStaffPicksMessage.visibility = View.VISIBLE
            }
        }
    }

    private fun updateFavoritesState(state: HomeUIState) {
        when (state) {
            Loading -> {
                favoritesLoadingBar.show()
                favoritesRecyclerView.visibility = View.INVISIBLE
                noFavoritesMessage.visibility = View.GONE
            }
            is ShowFavoriteMovies -> {
                noFavoritesMessage.visibility = View.GONE
                favoritesLoadingBar.hide()
                favoritesAdapter.submitList(state.movies)
                favoritesRecyclerView.visibility = View.VISIBLE
                favoritesRecyclerView.scrollToPosition(0)
            }
            else -> {
                favoritesLoadingBar.hide()
                noFavoritesMessage.visibility = View.VISIBLE
                favoritesRecyclerView.visibility = View.INVISIBLE
            }
        }
    }

    private fun initFavoritesRecyclerView(view: View) {
        favoritesRecyclerView = view.findViewById(R.id.favorites_rv)
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        favoritesAdapter = FavoriteMoviesAdapter(
            onMovieClick = { movieId ->
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToDetailFragment(movieId)
                )
            },
            onSeeAllClick = {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToSearchFragment(true)
                )
            }
        )
        favoritesRecyclerView.layoutManager = layoutManager
        favoritesRecyclerView.adapter = favoritesAdapter
    }

    private fun initStaffPicksRecyclerView(view: View) {
        staffPicksRecyclerView = view.findViewById(R.id.staff_picks_rv)
        val layoutManager = LinearLayoutManager(this.context)
        staffPicksAdapter = MoviesAdapter(
            onClick = { movieId ->
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToDetailFragment(movieId)
                )
            },
            onFavoriteClick = { movieId, oldValue, newValue ->
                viewModel.onFavoriteClick(movieId, oldValue, newValue)
            }
        )
        staffPicksRecyclerView.layoutManager = layoutManager
        staffPicksRecyclerView.adapter = staffPicksAdapter
    }
}