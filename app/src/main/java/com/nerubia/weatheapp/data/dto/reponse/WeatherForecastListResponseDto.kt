package com.nerubia.weatheapp.data.dto.reponse

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherForecastListResponseDto(
    val list: List<WeatherForecastDto>
)