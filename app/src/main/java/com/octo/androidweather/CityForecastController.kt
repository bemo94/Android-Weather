package com.octo.androidweather

import com.octo.usecases.CityForecastInteractor

class CityForecastController(private val interactor: CityForecastInteractor) {
    fun loadCityForecast(city: String) {
        interactor.loadCityForecast(city)
    }
}