package com.octo.repository.database.realm;

import com.octo.repository.Forecast;

import java.util.Date;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

@RealmClass
public class RealmForecast implements Forecast, RealmModel {
    private String cityName;
    private Float temperature;
    private Date date;

    @Override
    public Float getTemperature() {
        return temperature;
    }

    @Override
    public Date getDate() {
        return date;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
