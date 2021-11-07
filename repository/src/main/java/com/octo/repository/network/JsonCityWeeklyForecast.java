package com.octo.repository.network;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.auto.value.AutoValue;

import java.util.List;

@AutoValue
@JsonDeserialize(builder = AutoValue_JsonCityWeeklyForecast.Builder.class)
public abstract class JsonCityWeeklyForecast {
    public abstract JsonCity getCity();

    public abstract List<JsonForecast> getForecasts();

    @SuppressWarnings("unused")
    @AutoValue.Builder
    abstract static class Builder {
        @JsonProperty("city")
        abstract Builder setCity(final JsonCity city);

        @JsonProperty("list")
        abstract Builder setForecasts(final List<JsonForecast> forecasts);

        abstract JsonCityWeeklyForecast build();
    }

}
