package com.octo.usecases;

public interface CityForecastPresenter {
    void onEmptyInput();

    void onUnknownCity();

    void onGenericException();

    void onUnavailableForecasts();

    void onForecasts(Forecast worstForecast, Forecast bestForecast);
}
