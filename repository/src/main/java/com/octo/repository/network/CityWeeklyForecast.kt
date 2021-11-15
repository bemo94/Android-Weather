package com.octo.repository.network

data class CityWeeklyForecast(
    val cityName: String,
    val forecasts: List<Forecast>
)