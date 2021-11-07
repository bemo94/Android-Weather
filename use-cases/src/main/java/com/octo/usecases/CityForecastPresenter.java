package com.octo.usecases;

public interface CityForecastPresenter {
    void onEmptyInput();

    void onUnknownCity();

    void onGenericException();

    void onUnavailableForecasts();

    void onForecasts(String city, Forecast worstForecast, Forecast bestForecast);
}
