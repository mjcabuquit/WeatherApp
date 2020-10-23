package com.nerubia.weatheapp.di

import com.nerubia.weatheapp.data.local.WeatherForecastLocal
import com.nerubia.weatheapp.data.local.WeatherForecastLocalImpl
import com.nerubia.weatheapp.data.repository.WeatherForecastRepository
import com.nerubia.weatheapp.data.repository.WeatherForecastRepositoryImpl
import com.nerubia.weatheapp.data.services.WeatherForecastService
import com.nerubia.weatheapp.ui.details.WeatherForecastDetailsViewModel
import com.nerubia.weatheapp.ui.forecast.WeatherForecastViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val appModule = module {
    single {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
    }

    viewModel { WeatherForecastViewModel(repository = get()) }

    viewModel { (id: String) -> WeatherForecastDetailsViewModel(id, get()) }

    factory<WeatherForecastRepository> {
        WeatherForecastRepositoryImpl(
            local = get(),
            service = get()
        )
    }

    single<WeatherForecastLocal> { WeatherForecastLocalImpl() }

    single { get<Retrofit>().create(WeatherForecastService::class.java) }
}