package com.nerubia.weatheapp.data.dto.reponse

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherDto(
    val id: Double,
    val main: String,
    val description: String,
    val icon: String
)