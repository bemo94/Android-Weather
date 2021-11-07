package com.octo.usecases;

public interface CityForecastRepository {
    CityWeeklyForecast loadCityForecast(final String cityName) throws GenericException, UnknownCityException;

    class GenericException extends Exception {

    }

    class UnknownCityException extends Exception {

    }
}
