package com.canesvenatici.movies.ui.features.detail

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.canesvenatici.movies.R
import com.canesvenatici.movies.ui.features.detail.adapter.GenresAdapter
import com.canesvenatici.movies.ui.models.MovieDetailsUIModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : Fragment() {

    private val viewModel: DetailViewModel by viewModel<DetailViewModel>()

    private val args: DetailFragmentArgs by navArgs()

    private lateinit var genresAdapter: GenresAdapter

    private lateinit var loadingBar : ContentLoadingProgressBar
    private lateinit var genresRecyclerView : RecyclerView
    private lateinit var favoriteCheckBox : CheckBox
    private lateinit var ratingBar : RatingBar
    private lateinit var dateTimeTextView : TextView
    private lateinit var titleAndYearTextView : TextView
    private lateinit var yearTextView : TextView
    private lateinit var overviewContentTextView : TextView
    private lateinit var budgetContentTextView : TextView
    private lateinit var revenueContentTextView : TextView
    private lateinit var languageContentTextView : TextView
    private lateinit var ratingContentTextView : TextView
    private lateinit var posterView : ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI(view)

        viewModel.getMovie(args.movieId)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest { state ->
                    updateUI(state)
                }
            }
        }
    }

    private fun updateUI(state: DetailsUIState) {
        when (state) {
            is Details -> showDetails(state.movie)
            is DetailError -> {
                loadingBar.hide()
                Toast.makeText(
                    this@DetailFragment.context,
                    getString(R.string.something_wrong_message),
                    Toast.LENGTH_SHORT
                ).show()
            }
            Loading -> {
                loadingBar.show()
            }
        }
    }

    private fun initUI(view: View) {
        initRecyclerView(view)
        loadingBar = view.findViewById(R.id.details_pb)
        ratingBar = view.findViewById(R.id.rating_bar)
        dateTimeTextView = view.findViewById(R.id.date_time_tv)
        titleAndYearTextView = view.findViewById(R.id.title_year_tv)
        overviewContentTextView = view.findViewById(R.id.overview_tv)
        budgetContentTextView = view.findViewById(R.id.budget_tv)
        revenueContentTextView = view.findViewById(R.id.revenue_tv)
        languageContentTextView = view.findViewById(R.id.language_tv)
        ratingContentTextView = view.findViewById(R.id.rating_tv)
        favoriteCheckBox = view.findViewById(R.id.favorite_cb)
        posterView = view.findViewById(R.id.poster_iv)

        favoriteCheckBox.buttonTintList = null
        favoriteCheckBox.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onFavoriteClick(args.movieId, isChecked)
        }

        view.findViewById<ImageButton>(R.id.close_btn).setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun showDetails(movie: MovieDetailsUIModel) {
        this.context?.let {
            Glide
                .with(it)
                .load(movie.posterUrl)
                .into(posterView)
        }

        ratingBar.rating = movie.rating
        dateTimeTextView.text = movie.dateAndRuntime
        titleAndYearTextView.text = getStyledString(movie.titleAndYear)
        overviewContentTextView.text = movie.overview
        budgetContentTextView.text = movie.budget
        revenueContentTextView.text = movie.revenue
        languageContentTextView.text = movie.language
        ratingContentTextView.text = movie.reviews
        favoriteCheckBox.isChecked = movie.isFavorite

        genresAdapter.submitList(movie.genres)

        loadingBar.hide()
    }

    private fun initRecyclerView(view: View) {
        genresRecyclerView = view.findViewById(R.id.genres_rv)
        val layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        genresAdapter = GenresAdapter()
        genresRecyclerView.layoutManager = layoutManager
        genresRecyclerView.adapter = genresAdapter
    }

    private fun getStyledString(input: String): SpannableString {
        val spannableString = SpannableString(input)

        spannableString.setSpan(
            StyleSpan(Typeface.BOLD),
            0,
            input.length - 6, // six last symbols are a year, (2020)
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannableString.setSpan(
            StyleSpan(Typeface.NORMAL),
            input.length - 6,
            input.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannableString.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.black_70, this.context?.theme)),
            input.length - 6,
            input.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        return spannableString
    }

}