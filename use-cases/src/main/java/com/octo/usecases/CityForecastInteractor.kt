package com.octo.usecases

import com.octo.repository.network.CityWeeklyForecast
import com.octo.repository.network.Forecast
import com.octo.repository.network.WeatherNetworkRepository
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class CityForecastInteractor(
    private val repository: WeatherNetworkRepository,
    private val presenter: CityForecastPresenter
) {

    companion object {
        private const val MINIMUM_RELEVANT_HOUR = 8
        private const val MAXIMUM_RELEVANT_HOUR = 20
        private const val REQUIRED_FORECASTS = 2
    }

    fun loadCityForecast(cityName: String) {
        if (StringUtils.isBlank(cityName)) {
            presenter.onEmptyInput()
        } else {
            handleValidCityName(cityName)
        }
    }

    private fun handleValidCityName(cityName: String) {
        try {
            repository.loadCityWeeklyForecast(cityName)?.let { cityWeeklyForecast ->
                handleRetrievedForecast(cityWeeklyForecast)
            }
        } catch (e: IOException) {
            presenter.onGenericException()
        }
    }

    private fun handleRetrievedForecast(cityForecast: CityWeeklyForecast) {
        val relevantForecasts: List<Forecast> = getRelevantForecasts(cityForecast)
        if (relevantForecasts.size < REQUIRED_FORECASTS) {
            presenter.onUnavailableForecasts()
        } else {
            val list = sortForecastsByAscendingTemperature(relevantForecasts)()
            presenter.onForecasts(worstForecast(list), bestForecast(list))
        }
    }

    private fun getRelevantForecasts(cityForecast: CityWeeklyForecast): List<Forecast> {
        val relevantForecasts: MutableList<Forecast> = ArrayList()
        for (forecast in cityForecast.forecasts) {
            if (isRelevant(forecast)) {
                relevantForecasts.add(forecast)
            }
        }
        return relevantForecasts
    }

    private fun sortForecastsByAscendingTemperature(relevantForecasts: List<Forecast>): () -> List<Forecast> = {
        relevantForecasts.sortedBy { it.temperature }
    }

    private fun bestForecast(sortedForecasts: List<Forecast>): Forecast {
        return sortedForecasts[sortedForecasts.size - 1]
    }

    private fun worstForecast(sortedForecasts: List<Forecast>): Forecast {
        return sortedForecasts[0]
    }

    private fun isRelevant(forecast: Forecast): Boolean {
        val calendar: Calendar = Calendar.getInstance()
        calendar.time = forecast.date
        val hours: Int = calendar.get(Calendar.HOUR_OF_DAY)
        return hours in MINIMUM_RELEVANT_HOUR..MAXIMUM_RELEVANT_HOUR
    }
}