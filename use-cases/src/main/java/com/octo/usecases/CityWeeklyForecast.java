package com.octo.usecases;

import java.util.List;

public interface    CityWeeklyForecast {
    String getCityName();

    List<Forecast> getForecasts();
}
