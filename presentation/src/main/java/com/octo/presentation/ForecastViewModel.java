package com.octo.presentation;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ForecastViewModel {
    public static Builder builder() {
        return new AutoValue_ForecastViewModel.Builder();
    }

    public abstract String getTitle();

    public abstract String getWorstTemperatureLabel();

    public abstract String getBestTemperatureLabel();

    public abstract String getBestForecastLabel();

    public abstract String getWorstForecastLabel();

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setTitle(String title);

        public abstract Builder setWorstTemperatureLabel(String worstTemperatureLabel);

        public abstract Builder setBestTemperatureLabel(String bestTemperatureLabel);

        public abstract Builder setBestForecastLabel(String bestForecastLabel);

        public abstract Builder setWorstForecastLabel(String worstForecastLabel);

        public abstract ForecastViewModel build();
    }
}
