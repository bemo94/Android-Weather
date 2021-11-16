package com.octo.presentation

import com.octo.repository.network.Forecast
import com.octo.usecases.CityForecastPresenter
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class CityForecastPresenterImpl(
    private val view: CityForecastView,
    private val dateUtilsWrapper: DateUtilsWrapper
) : CityForecastPresenter {
    private val normalDateFormat: DateFormat =
        SimpleDateFormat(NORMAL_DATE_FORMAT, Locale.getDefault())
    private val todayDateFormat: DateFormat =
        SimpleDateFormat(TODAY_DATE_FORMAT, Locale.getDefault())

    override fun onEmptyInput() {
        view.displayEmptyInput()
    }

    override fun onGenericException() {
        view.displayGenericException()
    }

    override fun onUnavailableForecasts() {
        view.displayUnavailableForecasts()
    }

    override fun onForecasts(city: String, worstForecast: Forecast, bestForecast: Forecast) {
        view.displayViewModel(
            ForecastViewModel(
                city = city,
                worstTemperatureLabel = getTemperatureLabel(worstForecast.temperature),
                bestTemperatureLabel = getTemperatureLabel(bestForecast.temperature),
                bestForecastLabel = getForecastLabel(worstForecast),
                worstForecastLabel = getForecastLabel(bestForecast)
            )
        )
    }

    private fun getForecastLabel(forecast: Forecast): String {
        val date: Date = forecast.date
        val dateFormatted: String = getDateFormat(date).format(date)
        return capitalize(dateFormatted)
    }

    private fun getTemperatureLabel(temperature: Float): String {
        val decimalFormat = DecimalFormat()
        decimalFormat.maximumFractionDigits = 0
        return decimalFormat.format(temperature)
    }

    private fun getDateFormat(date: Date): DateFormat {
        return if (dateUtilsWrapper.isToday(date)) todayDateFormat else normalDateFormat
    }

    private fun capitalize(string: String): String {
        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase()
    }

    companion object {
        private const val NORMAL_DATE_FORMAT = "EEEE 'à' H'h'"
        private const val TODAY_DATE_FORMAT = "'aujourd''hui à ' H'h'"
    }
}