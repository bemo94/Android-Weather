package com.octo.androidweather;

import com.octo.usecases.CityForecastInteractor;

public class CityForecastControllerImpl implements CityForecastController {
    private final CityForecastInteractor interactor;

    public CityForecastControllerImpl(final CityForecastInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void loadCityForecast(String city) {
        interactor.loadCityForecast(trimNonNullString(city));
    }

    private static String trimNonNullString(String string) {
        return string != null ? string.trim() : null;
    }
}
