package com.octo.usecases


import org.junit.Test
import org.mockito.BDDMockito.given

import com.octo.repository.network.Forecast

import com.octo.repository.network.CityWeeklyForecast
import com.octo.repository.network.WeatherNetworkRepository
import java.util.Locale
import de.bechte.junit.runners.context.HierarchicalContextRunner
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.BDDMockito.then
import org.mockito.Mockito.mock
import java.io.IOException
import java.text.DateFormat
import java.text.SimpleDateFormat

@RunWith(HierarchicalContextRunner::class)
class CityForecastInteractorTest {

    companion object {
        private const val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
        private const val CITY = "city"
    }

    inner class InvalidCityName {

        @InjectMocks
        lateinit var interactor: CityForecastInteractor

        @Mock
        lateinit var repository: WeatherNetworkRepository

        @Mock
        lateinit var presenter: CityForecastPresenter

        @Before
        fun setup() {
            MockitoAnnotations.openMocks(this)
        }

        @Test
        fun loadCityForecast_WhenCityNameIsEmpty() {
            // When
            interactor.loadCityForecast("")

            // Then
            then(presenter).should().onEmptyInput()
        }

        @Test
        fun loadCityForecast_WhenCityNameIsBlank() {
            // When
            interactor.loadCityForecast("  ")

            // Then
            then(presenter).should().onEmptyInput()
        }
    }

    inner class ValidCityName {

        @InjectMocks
        lateinit var interactor: CityForecastInteractor

        @Mock
        lateinit var repository: WeatherNetworkRepository

        @Mock
        lateinit var presenter: CityForecastPresenter

        private val dateFormat: DateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())

        @Mock
        lateinit var cityForecast: CityWeeklyForecast

        @Before
        fun setup() {
            MockitoAnnotations.openMocks(this)
        }

        @Test
        fun loadCityForecast_WhenGenericException() {
            // Given
            given(repository.loadCityWeeklyForecast(Companion.CITY)).willAnswer { throw IOException("Ooops") }
            // When
            interactor.loadCityForecast(Companion.CITY)

            // Then
            then(presenter).should().onGenericException()
        }

        @Test
        fun loadCityName_WhenNoForecastAreAvailable() {
            // Given
            given(cityForecast.forecasts).willReturn(emptyList())
            given(repository.loadCityWeeklyForecast(CITY)).willReturn(cityForecast)

            // When
            interactor.loadCityForecast(CITY)

            // Then
            then(presenter).should().onUnavailableForecasts()
        }

        @Test
        fun loadCityName_WhenLessThanTwoRelevantForecasts() {
            // Given
            val forecasts: List<Forecast> = listOf(
                getMockForecast("2017-10-01 21:30:00"),
                getMockForecast("2017-10-01 18:00:00"),
                getMockForecast("2017-10-01 07:30:00")
            )
            given(cityForecast.forecasts).willReturn(forecasts)
            given(repository.loadCityWeeklyForecast(CITY)).willReturn(cityForecast)

            // When
            interactor.loadCityForecast(CITY)

            // Then
            then(presenter).should().onUnavailableForecasts()
        }

        @Test
        fun loadCityName_WhenForecastsAreRelevant() {
            // Given
            val worstForecast = getMockForecast("2017-10-01 19:40:00", 14f)
            val bestForecast = getMockForecast("2017-10-01 16:00:00", 20f)
            val unrelevantForecast = getMockForecast("2017-10-01 07:30:00", 12f)
            val forecasts: List<Forecast> = listOf(worstForecast, bestForecast, unrelevantForecast)
            given(cityForecast.cityName).willReturn(Companion.CITY)
            given(cityForecast.forecasts).willReturn(forecasts)
            given(repository.loadCityWeeklyForecast(Companion.CITY)).willReturn(cityForecast)

            // When
            interactor.loadCityForecast(Companion.CITY)

            // Then
            then(presenter).should().onForecasts(Companion.CITY, worstForecast, bestForecast)
        }

        private fun getMockForecast(dateTime: String, temperature: Float): Forecast {
            val forecast: Forecast = mock(Forecast::class.java)
            given(forecast.date).willReturn(dateFormat.parse(dateTime))
            given(forecast.temperature).willReturn(temperature)
            return forecast
        }

        private fun getMockForecast(dateTime: String): Forecast {
            val forecast: Forecast = mock(Forecast::class.java)
            given(forecast.date).willReturn(dateFormat.parse(dateTime))
            return forecast
        }
    }
}