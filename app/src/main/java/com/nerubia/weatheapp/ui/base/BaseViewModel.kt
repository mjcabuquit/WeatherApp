package com.nerubia.weatheapp.ui.base

import android.accounts.NetworkErrorException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nerubia.weatheapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.lang.Exception
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class BaseViewModel : ViewModel() {
    private val _showErrorLiveData = MutableLiveData<Int>()
    val showErrorLiveData: LiveData<Int> = _showErrorLiveData

    protected fun launchWithErrorHandling(
        context: CoroutineContext = EmptyCoroutineContext,
        call: suspend CoroutineScope.() -> Unit
    ): Job = viewModelScope.launch(context) {
        try {
            call()
        } catch (e: Exception) {
            when(e) {
                is NetworkErrorException, is SocketTimeoutException, is UnknownHostException -> {
                    _showErrorLiveData.value = R.string.weather_forecast_no_connection
                }

                is HttpException -> {
                    _showErrorLiveData.value = R.string.weather_forecast_http_error
                }

                else -> {
                    _showErrorLiveData.value = R.string.weather_forecast_something_wrong
                }
            }
        }
    }
}