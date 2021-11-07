package com.octo.repository.network;

import com.google.auto.value.AutoValue;
import com.octo.repository.CityWeeklyForecast;
import com.octo.repository.Forecast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ForecastTransformer {

    public CityWeeklyForecast toCityWeeklyForecast(final JsonCityWeeklyForecast jsonWeeklyForecast) {
        final ArrayList<Forecast> forecasts = new ArrayList<>();
        for (JsonForecast jsonForecast : jsonWeeklyForecast.getForecasts()) {
            final AutoForecast forecast = AutoForecast.builder()
                .setDate(jsonForecast.getDate())
                .setTemperature(jsonForecast.getMain().getTemperature())
                .build();
            forecasts.add(forecast);
        }

        return AutoCityWeeklyForecast.builder()
            .setCityName(jsonWeeklyForecast.getCity().getName())
            .setForecasts(forecasts)
            .build();
    }

    @AutoValue
    public static abstract class AutoCityWeeklyForecast implements CityWeeklyForecast {
        static Builder builder() {
            return new AutoValue_ForecastTransformer_AutoCityWeeklyForecast.Builder();
        }

        @AutoValue.Builder
        abstract static class Builder {
            abstract Builder setCityName(final String cityName);

            abstract Builder setForecasts(final List<Forecast> forecasts);

            abstract AutoCityWeeklyForecast build();
        }
    }

    @AutoValue
    public static abstract class AutoForecast implements Forecast {
        static Builder builder() {
            return new AutoValue_ForecastTransformer_AutoForecast.Builder();
        }

        @AutoValue.Builder
        abstract static class Builder {
            abstract Builder setTemperature(final Float temperature);

            abstract Builder setDate(final Date date);

            abstract AutoForecast build();
        }
    }
}
