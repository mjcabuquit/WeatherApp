package com.nerubia.weatheapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherModel(
    val id: Double,
    val main: String,
    val description: String,
    val icon: String
) : Parcelable