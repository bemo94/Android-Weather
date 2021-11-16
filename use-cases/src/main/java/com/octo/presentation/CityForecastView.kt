package com.octo.presentation

interface CityForecastView {
    fun displayEmptyInput()
    fun displayGenericException()
    fun displayUnavailableForecasts()
    fun displayViewModel(viewModel: ForecastViewModel)
}