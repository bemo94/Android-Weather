package com.octo.usecases

import com.octo.presentation.CityForecastDisplayableBuilder
import com.octo.presentation.ForecastDisplayable
import com.octo.repository.network.CityWeeklyForecast
import com.octo.repository.network.Forecast
import com.octo.repository.network.WeatherNetworkRepository
import java.io.IOException
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class CityForecastInteractor @Inject constructor (
    private val repository: WeatherNetworkRepository,
    private val displayableBuilder: CityForecastDisplayableBuilder
) {

    companion object {
        private const val MINIMUM_RELEVANT_HOUR = 8
        private const val MAXIMUM_RELEVANT_HOUR = 20
        private const val REQUIRED_FORECASTS = 2
    }

    sealed class State {
        data class Success(val displayable: ForecastDisplayable) : State()
        data class Error(val message: String) : State()
    }

    fun getCityForecastState(cityName: String) = if (StringUtils.isBlank(cityName)) {
        State.Error("Error city")
    } else {
        getHandledValidCityNameState(cityName)
    }

    private fun getHandledValidCityNameState(cityName: String): State = try {
        repository.loadCityWeeklyForecast(town = cityName)?.let { cityWeeklyForecast ->
            getHandleRetrievedForecastState(cityWeeklyForecast)
        } ?: State.Error("Error city")
    } catch (e: IOException) {
        State.Error("Error city")
    }

    private fun getHandleRetrievedForecastState(cityForecast: CityWeeklyForecast): State {
        val relevantForecasts: List<Forecast> = getRelevantForecasts(cityForecast)
        return if (getRelevantForecasts(cityForecast).size < REQUIRED_FORECASTS) {
            State.Error("Error city")
        } else {
            sortForecastsByAscendingTemperature(relevantForecasts)().let { sortedList ->
                State.Success(
                    displayable = displayableBuilder.buildForecastsDisplayable(
                        cityForecast.cityName,
                        worstForecast(sortedList),
                        bestForecast(sortedList)
                    )
                )
            }
        }
    }

    private fun getRelevantForecasts(cityForecast: CityWeeklyForecast): List<Forecast> = cityForecast.forecasts.mapNotNull { forecast ->
        if (isRelevant(forecast)) {
            forecast
        } else {
            null
        }
    }

    private fun sortForecastsByAscendingTemperature(relevantForecasts: List<Forecast>): () -> List<Forecast> = {
        relevantForecasts.sortedBy { it.temperature }
    }

    private fun bestForecast(sortedForecasts: List<Forecast>) = sortedForecasts[sortedForecasts.size - 1]

    private fun worstForecast(sortedForecasts: List<Forecast>) = sortedForecasts[0]

    private fun isRelevant(forecast: Forecast): Boolean {
        return getHourFromDate(forecast.date) in MINIMUM_RELEVANT_HOUR..MAXIMUM_RELEVANT_HOUR
    }

    private fun getHourFromDate(date: Date): Int {
        val calendar: Calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.get(Calendar.HOUR_OF_DAY)
    }
}