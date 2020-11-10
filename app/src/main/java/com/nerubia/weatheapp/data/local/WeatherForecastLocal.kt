package com.nerubia.weatheapp.data.local

interface WeatherForecastLocal {
    fun isFavorite(id: String): Boolean

    fun toggleFavorite(id: String)
}