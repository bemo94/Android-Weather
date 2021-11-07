package com.octo.repository.realm;

import com.octo.usecases.CityWeeklyForecast;
import com.octo.usecases.Forecast;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class RealmCityWeeklyForecast implements CityWeeklyForecast, RealmModel {
    @PrimaryKey
    private String cityName;
    private RealmList<RealmForecast> forecasts;

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setForecasts(RealmList<RealmForecast> forecasts) {
        this.forecasts = forecasts;
    }

    @Override
    public String getCityName() {
        return cityName;
    }

    @Override
    public List<Forecast> getForecasts() {
        return new ArrayList<Forecast>(forecasts);
    }
}
