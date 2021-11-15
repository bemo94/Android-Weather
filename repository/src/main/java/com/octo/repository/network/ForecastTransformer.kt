package com.octo.repository.network

class ForecastTransformer {

    fun toCityWeeklyForecast(jsonWeeklyForecast: JsonCityWeeklyForecast) = CityWeeklyForecast(
        forecasts = jsonWeeklyForecast.forecasts.map { jsonForecast ->
            Forecast(
                date = jsonForecast.date,
                temperature = jsonForecast.main.temperature
            )
        },
        cityName = jsonWeeklyForecast.city.name
    )
}