package com.octo.androidweather

import com.octo.usecases.CityForecastInteractor

import com.octo.presentation.CityForecastPresenterImpl
import com.octo.presentation.DateUtilsWrapper
import com.octo.repository.network.NetworkModule
import com.octo.repository.network.WeatherNetworkRepository

import com.octo.usecases.CityForecastPresenter
import okhttp3.HttpUrl


class CityForecastModule(activity: MainActivity) {
    val controller: CityForecastController

    init {
        val outputPort: CityForecastPresenter =
            CityForecastPresenterImpl(activity, DateUtilsWrapper())
        val interactor = CityForecastInteractor(
            WeatherNetworkRepository(
                NetworkModule().getRetrofit(
                    HttpUrl.get(BuildConfig.WEATHER_BASE_URL)
                )
            ), outputPort
        )
        controller = CityForecastController(interactor)
    }
}