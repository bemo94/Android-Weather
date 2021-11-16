package com.octo.usecases

import com.octo.repository.network.Forecast

interface CityForecastPresenter {
    fun onEmptyInput()
    fun onGenericException()
    fun onUnavailableForecasts()
    fun onForecasts(city: String, worstForecast: Forecast, bestForecast: Forecast)
}