package com.octo.repository.network;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.auto.value.AutoValue;

import java.util.Date;

@AutoValue
@JsonDeserialize(builder = AutoValue_JsonForecast.Builder.class)
public abstract class JsonForecast {
    public abstract JsonMain getMain();

    public abstract Date getDate();

    @SuppressWarnings("unused")
    @AutoValue.Builder
    abstract static class Builder {
        @JsonProperty("main")
        abstract Builder setMain(final JsonMain jsonMain);

        @JsonProperty("dt_txt")
        abstract Builder setDate(final Date date);

        abstract JsonForecast build();
    }
}
