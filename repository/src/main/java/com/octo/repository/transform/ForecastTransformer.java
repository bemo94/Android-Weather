package com.octo.repository.transform;

import com.octo.repository.network.JsonCityWeeklyForecast;
import com.octo.repository.network.JsonForecast;
import com.octo.repository.realm.RealmCityWeeklyForecast;
import com.octo.repository.realm.RealmForecast;
import com.octo.usecases.CityWeeklyForecast;
import com.octo.usecases.Forecast;

import java.util.ArrayList;

import io.realm.RealmList;

public class ForecastTransformer {

    public CityWeeklyForecast toCityWeeklyForecast(final JsonCityWeeklyForecast jsonWeeklyForecast) {
        final ArrayList<Forecast> forecasts = new ArrayList<>();
        for (JsonForecast jsonForecast : jsonWeeklyForecast.getForecasts()) {
            final AutoForecast forecast = toAutoForecast(jsonForecast);
            forecasts.add(forecast);
        }

        return toAutoCityWeeklyForecast(jsonWeeklyForecast, forecasts);
    }

    public RealmCityWeeklyForecast toRealmCityWeeklyForecast(final CityWeeklyForecast cityForecast) {
        final RealmList<RealmForecast> realmForecasts = new RealmList<>();
        for (Forecast forecast : cityForecast.getForecasts()) {
            realmForecasts.add(toRealmForecast(cityForecast, forecast));
        }
        return toRealmCityWeeklyForecast(cityForecast, realmForecasts);
    }

    private AutoCityWeeklyForecast toAutoCityWeeklyForecast(
        final JsonCityWeeklyForecast jsonWeeklyForecast,
        final ArrayList<Forecast> forecasts
    ) {
        return AutoCityWeeklyForecast.builder()
            .setCityName(jsonWeeklyForecast.getCity().getName())
            .setForecasts(forecasts)
            .build();
    }

    private AutoForecast toAutoForecast(JsonForecast jsonForecast) {
        return AutoForecast.builder()
            .setDate(jsonForecast.getDate())
            .setTemperature(jsonForecast.getMain().getTemperature())
            .build();
    }

    private RealmCityWeeklyForecast toRealmCityWeeklyForecast(
        final CityWeeklyForecast cityForecast,
        final RealmList<RealmForecast> realmForecasts
    ) {
        final RealmCityWeeklyForecast realmCityForecast = new RealmCityWeeklyForecast();
        realmCityForecast.setCityName(cityForecast.getCityName());
        realmCityForecast.setForecasts(realmForecasts);
        return realmCityForecast;
    }

    private RealmForecast toRealmForecast(
        final CityWeeklyForecast cityForecast,
        final Forecast forecast
    ) {
        final RealmForecast realmForecast = new RealmForecast();
        realmForecast.setCityName(cityForecast.getCityName());
        realmForecast.setTemperature(forecast.getTemperature());
        realmForecast.setDate(forecast.getDate());
        return realmForecast;
    }
}
