package com.nerubia.weatheapp.data.local

class WeatherForecastLocalImpl : WeatherForecastLocal {
    private val favorites: MutableMap<String, Boolean> = mutableMapOf()

    override fun isFavorite(id: String): Boolean = favorites[id] ?: false

    override fun toggleFavorite(id: String) {
        favorites[id] = isFavorite(id).not()
    }
}