package com.octo.repository.realm;

import com.octo.repository.transform.ForecastTransformer;
import com.octo.usecases.CityWeeklyForecast;

import io.realm.Realm;

public class WeatherRealmRepository {
    private final Realm realm;

    public WeatherRealmRepository(final Realm realm) {
        this.realm = realm;
    }

    public void writeCityWeeklyForecast(final CityWeeklyForecast cityForecast) {
        final ForecastTransformer transformer = new ForecastTransformer();
        final RealmCityWeeklyForecast realmCityForecast = transformer.toRealmCityWeeklyForecast(cityForecast);
        realm.beginTransaction();

        realm.where(RealmForecast.class)
            .equalTo("cityName", cityForecast.getCityName())
            .findAll()
            .deleteAllFromRealm();

        realm.copyToRealmOrUpdate(realmCityForecast);
        realm.commitTransaction();
    }

    public CityWeeklyForecast loadCityWeeklyForecast(final String cityName) {
        return realm.where(RealmCityWeeklyForecast.class)
            .equalTo("cityName", cityName)
            .findFirst();
    }
}
