package com.octo.repository.transform;

import com.octo.repository.CityWeeklyForecast;
import com.octo.repository.Forecast;
import com.octo.repository.network.JsonCityWeeklyForecast;
import com.octo.repository.network.JsonForecast;

import java.util.ArrayList;
import java.util.List;

public class ForecastTransformer {

    public CityWeeklyForecast toCityWeeklyForecast(final JsonCityWeeklyForecast jsonWeeklyForecast) {
        final List<Forecast> forecasts = new ArrayList<>();
        for (JsonForecast jsonForecast : jsonWeeklyForecast.getForecasts()) {
            forecasts.add(toAutoForecast(jsonForecast));
        }
        return toAutoCityWeeklyForecast(jsonWeeklyForecast, forecasts);
    }

    private AutoCityWeeklyForecast toAutoCityWeeklyForecast(
        final JsonCityWeeklyForecast jsonWeeklyForecast,
        final List<Forecast> forecasts
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
}
