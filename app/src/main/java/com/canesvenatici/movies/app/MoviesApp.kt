package com.canesvenatici.movies.app

import android.app.Application
import com.canesvenatici.movies.data.di.dataModule
import com.canesvenatici.movies.domain.di.domainModule
import com.canesvenatici.movies.ui.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MoviesApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MoviesApp)
            modules(
                dataModule,
                domainModule,
                presentationModule
            )
        }
    }
}
