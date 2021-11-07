package com.octo.repository.transform;

import com.google.auto.value.AutoValue;
import com.octo.repository.CityWeeklyForecast;
import com.octo.repository.Forecast;

import java.util.List;


@AutoValue
public abstract class AutoCityWeeklyForecast implements CityWeeklyForecast {
    public static Builder builder() {
        return new AutoValue_AutoCityWeeklyForecast.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setCityName(final String cityName);

        public abstract Builder setForecasts(final List<Forecast> forecasts);

        public abstract AutoCityWeeklyForecast build();
    }
}
