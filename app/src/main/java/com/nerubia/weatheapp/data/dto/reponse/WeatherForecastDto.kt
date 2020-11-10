package com.nerubia.weatheapp.data.dto.reponse

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherForecastDto(
    val weather: List<WeatherDto>,
    val main: MainDto,
    val id: String,
    val name: String
)