package com.nerubia.weatheapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherForecastModel(
    val weather: List<WeatherModel>,
    val main: MainModel,
    val id: String,
    val name: String,
    var favorite: Boolean = false
) : Parcelable