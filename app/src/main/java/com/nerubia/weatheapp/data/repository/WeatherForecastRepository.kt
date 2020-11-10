package com.nerubia.weatheapp.data.repository

import com.nerubia.weatheapp.data.model.WeatherForecastModel
import kotlinx.coroutines.flow.Flow

interface WeatherForecastRepository {
    suspend fun getWeatherForecast(id: List<String>): List<WeatherForecastModel>

    suspend fun getWeatherById(id: String): WeatherForecastModel?

    fun toggleFavorite(id: String)

    fun observePendingUpdate(): Flow<Unit>
}