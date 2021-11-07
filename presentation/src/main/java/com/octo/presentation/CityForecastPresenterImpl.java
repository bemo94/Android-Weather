package com.octo.presentation;

import android.content.res.Resources;

import com.octo.usecases.CityForecastPresenter;
import com.octo.usecases.Forecast;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CityForecastPresenterImpl implements CityForecastPresenter {
    private static final String NORMAL_DATE_FORMAT = "EEEE 'à' H'h'";
    private static final String TODAY_DATE_FORMAT = "'aujourd''hui à ' H'h'";
    private final DateFormat normalDateFormat = new SimpleDateFormat(NORMAL_DATE_FORMAT, Locale.getDefault());
    private final DateFormat todayDateFormat = new SimpleDateFormat(TODAY_DATE_FORMAT, Locale.getDefault());
    private final CityForecastView view;
    private final Resources resources;
    private final DateUtilsInjector dateUtilsInjector;

    public CityForecastPresenterImpl(final CityForecastView view, final Resources resources, final DateUtilsInjector dateUtilsInjector) {
        this.dateUtilsInjector = dateUtilsInjector;
        this.view = view;
        this.resources = resources;
    }

    @Override
    public void onEmptyInput() {
        view.displayMessage(resources.getString(R.string.empty_input));
    }

    @Override
    public void onUnknownCity() {
        view.displayMessage(resources.getString(R.string.unknown_city));
    }

    @Override
    public void onGenericException() {
        view.displayMessage(resources.getString(R.string.unavailable_forecasts));
    }

    @Override
    public void onUnavailableForecasts() {
        view.displayMessage(resources.getString(R.string.unavailable_forecasts));
    }

    @Override
    public void onForecasts(String city, Forecast worstForecast, Forecast bestForecast) {
        view.displayViewModel(ForecastViewModel.builder()
            .setTitle(resources.getString(R.string.city_forecast, city))
            .setWorstTemperatureLabel(getTemperatureLabel(worstForecast.getTemperature()))
            .setBestTemperatureLabel(getTemperatureLabel(bestForecast.getTemperature()))
            .setWorstForecastLabel(getForecastLabel(worstForecast))
            .setBestForecastLabel(getForecastLabel(bestForecast))
            .build());
    }

    private String getForecastLabel(Forecast forecast) {
        final Date date = forecast.getDate();
        final String dateFormatted = getDateFormat(date).format(date);
        return capitalize(dateFormatted);
    }

    private String getTemperatureLabel(final Float temperature) {
        final DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(0);
        return resources.getString(R.string.temperature_label, decimalFormat.format(temperature));
    }

    private DateFormat getDateFormat(Date date) {
        return dateUtilsInjector.isToday(date) ? todayDateFormat : normalDateFormat;
    }

    private String capitalize(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }
}
