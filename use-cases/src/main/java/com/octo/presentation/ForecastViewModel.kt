package com.octo.presentation

data class ForecastViewModel(
    val city: String,
    val worstTemperatureLabel: String,
    val bestTemperatureLabel: String,
    val bestForecastLabel: String,
    val worstForecastLabel: String
)