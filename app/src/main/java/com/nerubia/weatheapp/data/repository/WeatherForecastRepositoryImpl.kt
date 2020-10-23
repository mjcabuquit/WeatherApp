package com.nerubia.weatheapp.data.repository

import com.nerubia.weatheapp.data.dto.transformer.transform
import com.nerubia.weatheapp.data.local.WeatherForecastLocal
import com.nerubia.weatheapp.data.model.WeatherForecastModel
import com.nerubia.weatheapp.data.service.WeatherForecastService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

@ExperimentalCoroutinesApi
class WeatherForecastRepositoryImpl(
    private val local: WeatherForecastLocal,
    private val service: WeatherForecastService
) : WeatherForecastRepository {
    private val pendingUpdates = ConflatedBroadcastChannel(Unit)

    override suspend fun getWeatherForecast(id: List<String>): List<WeatherForecastModel> =
        service.getWeatherUpdate(id.joinToString(",")).list.map {
            it.transform(local.isFavorite(it.id))
        }

    override suspend fun getWeatherById(id: String): WeatherForecastModel? =
        service.getWeatherByCity(id).transform(local.isFavorite(id))

    override fun toggleFavorite(id: String) {
        local.toggleFavorite(id)
        pendingUpdates.offer(Unit)
    }

    override fun observePendingUpdate(): Flow<Unit> = pendingUpdates.asFlow()
}