package com.octo.repository.database.realm;

import com.octo.repository.CityWeeklyForecast;

import io.realm.Realm;

public class WeatherRealmRepository {
    private final Realm realm;

    public WeatherRealmRepository(final Realm realm) {
        this.realm = realm;
    }

    public void writeCityWeeklyForecast(final CityWeeklyForecast cityForecast) {
    }

    public CityWeeklyForecast loadCityWeeklyForecast(final String cityName) {
        return null;
    }
}
