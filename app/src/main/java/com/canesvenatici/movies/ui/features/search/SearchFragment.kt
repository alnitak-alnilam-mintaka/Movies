package com.canesvenatici.movies.ui.features.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.canesvenatici.movies.R
import com.canesvenatici.movies.ui.movies_adapter.MoviesAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModel<SearchViewModel>()

    private val args: SearchFragmentArgs by navArgs()

    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var moviesRecyclerView : RecyclerView
    private lateinit var loadingBar : ContentLoadingProgressBar
    private lateinit var title : TextView
    private lateinit var errorMessage : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI(view)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collectLatest { state ->
                    updateUIState(state)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.titleState.collectLatest { state ->
                    title.text = getString(state.stringRes)
                }
            }
        }
        viewModel.getMovies("", args.favorites)
    }

    private fun updateUIState(state: SearchUIState) {
        when (state) {
            Loading -> {
                loadingBar.show()
                moviesRecyclerView.visibility = View.INVISIBLE
                errorMessage.visibility = View.GONE
            }
            is ShowMovies -> {
                loadingBar.hide()
                moviesAdapter.submitList(state.movies)
                moviesRecyclerView.visibility = View.VISIBLE
                errorMessage.visibility = View.GONE
            }
            else -> {
                loadingBar.hide()
                errorMessage.visibility = View.VISIBLE
            }
        }
    }

    private fun initUI(view: View) {
        initRecyclerView(view)
        initSearchView(view)
        loadingBar = view.findViewById(R.id.movies_pb)
        title = view.findViewById(R.id.title_tv)
        errorMessage = view.findViewById(R.id.error_tv)

        view.findViewById<ImageButton>(R.id.back_btn).setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initRecyclerView(view: View) {
        moviesRecyclerView = view.findViewById(R.id.movies_rv)
        val layoutManager = LinearLayoutManager(this.context)
        moviesAdapter = MoviesAdapter(
            onClick = { movieId ->
                findNavController().navigate(
                    SearchFragmentDirections.actionSearchFragmentToDetailFragment(movieId)
                )
            },
            onFavoriteClick = { movieId, oldValue, newValue ->
                viewModel.onFavoriteClick(movieId, oldValue, newValue)
            }
        )
        moviesRecyclerView.layoutManager = layoutManager
        moviesRecyclerView.adapter = moviesAdapter
    }

    private fun initSearchView(view: View) {
        view.findViewById<SearchView>(R.id.search_view).apply {
            isActivated = true
            setQueryHint(context.getString(R.string.search_hint))
            onActionViewExpanded()
            isIconified = false
            clearFocus()

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    viewModel.getMovies(newText, args.favorites)
                    return false
                }
            })
        }
    }
}