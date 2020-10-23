package com.nerubia.weatheapp.ui.forecast

import android.accounts.NetworkErrorException
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jraska.livedata.test
import com.nerubia.weatheapp.R
import com.nerubia.weatheapp.data.model.WeatherForecastModel
import com.nerubia.weatheapp.data.repository.WeatherForecastRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException

class WeatherForecastViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var mockRepository: WeatherForecastRepository

    private val MOCK_WEATHER_CITY_IDS = listOf("1701668", "3067696", "1835848")

    @Before
    fun setup() {
        Dispatchers.setMain(TestCoroutineDispatcher())
        MockKAnnotations.init(this)
    }

    @Test
    fun `when user loads the app, then show the list of weather`() {
        val expectedResult = mockk<List<WeatherForecastModel>>()

        coEvery { mockRepository.getWeatherForecast(MOCK_WEATHER_CITY_IDS) } returns expectedResult

        val mockViewModel = WeatherForecastViewModel(mockRepository)

        mockViewModel.loadWeatherUpdate()

        mockViewModel.weatherForecastListLiveData.test().assertValue(expectedResult).assertHistorySize(1)
    }

    @Test
    fun `when app returns no connection, then show no connection message`() {
        val expectedResult = R.string.weather_forecast_no_connection
        val mockResponse = mockk<NetworkErrorException>()

        coEvery { mockRepository.getWeatherForecast(MOCK_WEATHER_CITY_IDS) } throws mockResponse

        val mockViewModel = WeatherForecastViewModel(mockRepository)

        mockViewModel.loadWeatherUpdate()

        mockViewModel.showErrorLiveData.test().assertValue(expectedResult).assertHistorySize(1)
    }

    @Test
    fun `when list of weather forecast returns bad response, then show something went wrong message`() {
        val expectedResult = R.string.weather_forecast_http_error
        val mockResponse = mockk<HttpException>()

        coEvery { mockRepository.getWeatherForecast(MOCK_WEATHER_CITY_IDS) } throws mockResponse

        val mockViewModel = WeatherForecastViewModel(mockRepository)

        mockViewModel.loadWeatherUpdate()

        mockViewModel.showErrorLiveData.test().assertValue(expectedResult).assertHistorySize(1)
    }
}