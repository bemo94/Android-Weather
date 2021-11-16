package com.octo.presentation

import com.octo.repository.network.Forecast

import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.*
import org.mockito.MockitoAnnotations
import org.mockito.Mock
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class CityForecastPresenterImplTest {

    private val dateFormat: DateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())

    @Mock
    private lateinit var view: CityForecastView

    @Mock
    private lateinit var dateUtilsWrapper: DateUtilsWrapper
    private lateinit var presenter: CityForecastPresenterImpl

    @Before
    fun setup() {
        Locale.setDefault(Locale.FRANCE)
        MockitoAnnotations.initMocks(this)
        presenter = CityForecastPresenterImpl(view, dateUtilsWrapper)
    }

    @Test
    fun onEmptyInput() {
        presenter.onEmptyInput()
        then(view).should(only()).displayEmptyInput()
    }

    @Test
    fun onGenericException() {
        presenter.onGenericException()
        then(view).should(only()).displayGenericException()
    }

    @Test
    fun onUnavailableForecasts() {
        presenter.onUnavailableForecasts()
        then(view).should(only()).displayUnavailableForecasts()
    }

    @Test
    fun onForecast() {
        val worstForecast = getMockForecast("2016-10-04 19:40:00", 14.45f)
        val bestForecast = getMockForecast("2016-10-06 09:00:00", 20.57f)
        given(dateUtilsWrapper.isToday(Objects.requireNonNull(dateFormat.parse("2016-10-06 09:00:00")))).willReturn(
            false
        )
        given(dateUtilsWrapper.isToday(Objects.requireNonNull(dateFormat.parse("2016-10-04 19:40:00")))).willReturn(
            false
        )
        presenter.onForecasts("Paris", worstForecast, bestForecast)
        then(view).should().displayViewModel(
            ForecastViewModel(
                city = "Paris",
                worstTemperatureLabel = "14",
                bestTemperatureLabel = "21",
                bestForecastLabel = "Mardi à 19h",
                worstForecastLabel = "Jeudi à 9h",
            )
        )
    }

    @Test
    fun onForecast_WhenForecastIsToday() {
        val forecast = getMockForecast("2016-10-06 09:00:00", 14.45f)
        given(dateUtilsWrapper.isToday(Objects.requireNonNull(dateFormat.parse("2016-10-06 09:00:00")))).willReturn(
            true
        )
        presenter.onForecasts("Paris", forecast, forecast)
        then(view).should().displayViewModel(
            ForecastViewModel(
                city = "Paris",
                worstTemperatureLabel = "14",
                bestTemperatureLabel = "14",
                bestForecastLabel = "Aujourd'hui à  9h",
                worstForecastLabel = "Aujourd'hui à  9h",
            )
        )
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