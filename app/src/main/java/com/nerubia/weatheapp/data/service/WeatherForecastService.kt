package com.nerubia.weatheapp.data.service

import com.nerubia.weatheapp.data.dto.reponse.WeatherForecastDto
import com.nerubia.weatheapp.data.dto.reponse.WeatherForecastListResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherForecastService {

    @GET("data/2.5/group?units=metric")
    suspend fun getWeatherUpdate(
        @Query(
            value = "id",
            encoded = false
        ) id: String
    ): WeatherForecastListResponseDto

    @GET("data/2.5/weather?units=metric")
    suspend fun getWeatherByCity(@Query("id") id: String): WeatherForecastDto
}