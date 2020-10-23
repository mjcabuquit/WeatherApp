package com.nerubia.weatheapp.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nerubia.weatheapp.data.model.WeatherForecastModel
import com.nerubia.weatheapp.data.repository.WeatherForecastRepository
import com.nerubia.weatheapp.ui.base.BaseViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class WeatherForecastDetailsViewModel(
    private val id: String,
    private val repository: WeatherForecastRepository
) : BaseViewModel() {

    private val _weatherUpdateLiveData = MutableLiveData<WeatherForecastModel>()
    val weatherUpdateLiveData: LiveData<WeatherForecastModel> = _weatherUpdateLiveData

    private val _isFavoriteLiveData = MutableLiveData<Boolean>()
    val isFavoriteLiveData: LiveData<Boolean> = _isFavoriteLiveData

    init {
        loadWeatherForecast()
    }

    private fun loadWeatherForecast() {
        launchWithErrorHandling {
            _weatherUpdateLiveData.value = repository.getWeatherById(id).also {
                _isFavoriteLiveData.value = it?.favorite
            }
        }
    }

    fun onClickFavorite() {
        viewModelScope.launch {
            repository.toggleFavorite(id)
            _isFavoriteLiveData.apply {
                value = value?.not()
            }
        }
    }
}