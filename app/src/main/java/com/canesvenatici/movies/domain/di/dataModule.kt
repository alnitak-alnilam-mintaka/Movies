package com.canesvenatici.movies.domain.di

import com.canesvenatici.movies.domain.interactors.MoviesInteractor
import com.canesvenatici.movies.domain.interactors.MoviesInteractorImpl
import org.koin.dsl.module

val domainModule = module {
    single<MoviesInteractor> { MoviesInteractorImpl(get()) }
}