package com.nerubia.weatheapp.data.dto.transformer

import com.nerubia.weatheapp.data.dto.reponse.MainDto
import com.nerubia.weatheapp.data.dto.reponse.WeatherDto
import com.nerubia.weatheapp.data.dto.reponse.WeatherForecastDto
import com.nerubia.weatheapp.data.model.MainModel
import com.nerubia.weatheapp.data.model.WeatherForecastModel
import com.nerubia.weatheapp.data.model.WeatherModel

fun MainDto.transform(): MainModel =
    MainModel(
        temp = temp,
        feelsLike = feelsLike,
        tempMin = tempMin,
        tempMax = tempMax,
        pressure = pressure,
        humidity = humidity
    )

fun WeatherDto.transform(): WeatherModel =
    WeatherModel(
        id = id,
        main = main,
        description = description,
        icon = icon
    )

fun WeatherForecastDto.transform(isFavorite: Boolean): WeatherForecastModel =
    WeatherForecastModel(
        weather = weather.map {
            it.transform()
        },
        main = main.transform(),
        id = id,
        name = name,
        favorite = isFavorite
    )