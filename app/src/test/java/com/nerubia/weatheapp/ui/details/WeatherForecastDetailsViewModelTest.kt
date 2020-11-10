package com.nerubia.weatheapp.ui.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jraska.livedata.test
import com.nerubia.weatheapp.data.model.WeatherForecastModel
import com.nerubia.weatheapp.data.repository.WeatherForecastRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class WeatherForecastDetailsViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var mockRepository: WeatherForecastRepository

    private val MOCK_ID = "1701668"

    @Before
    fun setup() {
        Dispatchers.setMain(TestCoroutineDispatcher())
        MockKAnnotations.init(this)
    }

    @Test
    fun `when app loads the weather forecast details, then show details`() = runBlockingTest {
        val expectedResult= mockk<WeatherForecastModel>()
        val expectedFavorite = true

        coEvery { mockRepository.getWeatherById(MOCK_ID) } returns expectedResult
        every { expectedResult.favorite } returns expectedFavorite

        val mockViewModel = WeatherForecastDetailsViewModel(
            id = MOCK_ID,
            repository = mockRepository
        )

        mockViewModel.isFavoriteLiveData.test().assertValue(expectedFavorite).assertHistorySize(1)
        mockViewModel.weatherUpdateLiveData.test().assertValue(expectedResult).assertHistorySize(1)
    }

    @Test
    fun `when user clicks the city as favorite, then toggle favorite`() {
        val expectedResult = false
        val mockResult = true
        val mockWeatherForecast = mockk<WeatherForecastModel>()

        coEvery { mockRepository.getWeatherById(MOCK_ID) } returns mockWeatherForecast
        every { mockRepository.toggleFavorite(MOCK_ID) } returns Unit
        every { mockWeatherForecast.favorite } returns mockResult

        val mockViewModel = WeatherForecastDetailsViewModel(
            id = MOCK_ID,
            repository =  mockRepository
        )

        mockViewModel.onClickFavorite()

        mockViewModel.isFavoriteLiveData.test().assertValue(expectedResult).assertHistorySize(1)
    }
}