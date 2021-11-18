package com.octo.presentation

import com.octo.repository.network.Forecast
import junit.framework.Assert.assertEquals

import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.*
import org.mockito.InjectMocks
import org.mockito.MockitoAnnotations
import org.mockito.Mock
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class CityForecastDisplayableBuilderTest {

    private val dateFormat: DateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())

    @Mock
    private lateinit var dateUtilsInjector: DateUtilsInjector

    @InjectMocks
    private lateinit var displayableBuilder: CityForecastDisplayableBuilder

    @Before
    fun setup() {
        Locale.setDefault(Locale.FRANCE)
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun onForecast() {
        // Given
        val worstForecast = getMockForecast("2016-10-04 19:40:00", 14.45f)
        val bestForecast = getMockForecast("2016-10-06 09:00:00", 20.57f)
        given(dateUtilsInjector.isToday(Objects.requireNonNull(dateFormat.parse("2016-10-06 09:00:00")))).willReturn(false)
        given(dateUtilsInjector.isToday(Objects.requireNonNull(dateFormat.parse("2016-10-04 19:40:00")))).willReturn(false)

        // When
        val result = displayableBuilder.buildForecastsDisplayable("Paris", worstForecast, bestForecast)

        // Then
        val expected = ForecastDisplayable(
            city = "Paris",
            worstTemperatureLabel = "14",
            bestTemperatureLabel = "21",
            bestForecastLabel = "Mardi à 19h",
            worstForecastLabel = "Jeudi à 9h",
        )
        assertEquals(result, expected)
    }

    @Test
    fun onForecast_WhenForecastIsToday() {
        // Given
        val forecast = getMockForecast("2016-10-06 09:00:00", 14.45f)
        given(dateUtilsInjector.isToday(Objects.requireNonNull(dateFormat.parse("2016-10-06 09:00:00")))).willReturn(true)

        // When
        val result = displayableBuilder.buildForecastsDisplayable("Paris", forecast, forecast)

        // Then
        val expected = ForecastDisplayable(
            city = "Paris",
            worstTemperatureLabel = "14",
            bestTemperatureLabel = "14",
            bestForecastLabel = "Aujourd'hui à  9h",
            worstForecastLabel = "Aujourd'hui à  9h",
        )
        assertEquals(result, expected)
    }

    private fun getMockForecast(dateTime: String, temperature: Float): Forecast {
        val forecast: Forecast = mock(Forecast::class.java)
        given(forecast.date).willReturn(dateFormat.parse(dateTime))
        given(forecast.temperature).willReturn(temperature)
        return forecast
    }

    companion object {
        private const val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
    }
}