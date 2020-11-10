package com.nerubia.weatheapp.ui.forecast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nerubia.weatheapp.data.model.WeatherForecastModel
import com.nerubia.weatheapp.data.repository.WeatherForecastRepository
import com.nerubia.weatheapp.ui.base.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@ExperimentalCoroutinesApi
class WeatherForecastViewModel(private val repository: WeatherForecastRepository) : BaseViewModel() {
    private val _weatherForecastListLiveData = MutableLiveData<List<WeatherForecastModel>>()
    val weatherForecastListLiveData: LiveData<List<WeatherForecastModel>> = _weatherForecastListLiveData

    private val WEATHER_CITY_IDS = listOf("1701668", "3067696", "1835848")

    fun observe() {
        repository.observePendingUpdate().onEach {
            loadWeatherUpdate()
        }.launchIn(viewModelScope)
    }

    fun loadWeatherUpdate() {
        launchWithErrorHandling {
            _weatherForecastListLiveData.value =
                repository.getWeatherForecast(WEATHER_CITY_IDS)
        }
    }
}