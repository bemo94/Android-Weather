package com.octo.androidweather;

import com.octo.usecases.CityForecastInteractor;

class CityForecastController {
    private final CityForecastInteractor interactor;

    public CityForecastController(final CityForecastInteractor interactor) {
        this.interactor = interactor;
    }

    public void loadCityForecast(String city) {
        interactor.loadCityForecast(trimNonNullString(city));
    }

    private static String trimNonNullString(String string) {
        return string != null ? string.trim() : null;
    }
}
