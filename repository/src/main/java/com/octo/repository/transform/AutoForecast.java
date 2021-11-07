package com.octo.repository.transform;

import com.google.auto.value.AutoValue;
import com.octo.repository.Forecast;

import java.util.Date;

@AutoValue
public abstract class AutoForecast implements Forecast {
    public static Builder builder() {
        return new AutoValue_AutoForecast.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setTemperature(final Float temperature);

        public abstract Builder setDate(final Date date);

        public abstract AutoForecast build();
    }
}
