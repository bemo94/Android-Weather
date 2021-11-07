package com.octo.repository;

import java.util.List;

public interface CityWeeklyForecast {
    String getCityName();

    List<Forecast> getForecasts();
}
