package com.canesvenatici.movies.ui.di

import com.canesvenatici.movies.ui.features.detail.DetailViewModel
import com.canesvenatici.movies.ui.features.home.HomeViewModel
import com.canesvenatici.movies.ui.features.search.SearchViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { HomeViewModel(get(), get()) }
    viewModel { DetailViewModel(get(), get()) }
    viewModel { SearchViewModel(get(), get()) }
    single { provideIODispatcher() }
}

fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO