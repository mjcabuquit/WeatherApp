package com.nerubia.weatheapp

import android.app.Application
import com.nerubia.weatheapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WeatherApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@WeatherApplication)
            modules(listOf(appModule))
        }
    }
}