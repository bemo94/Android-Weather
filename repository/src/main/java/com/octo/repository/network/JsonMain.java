package com.octo.repository.network;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class JsonMain {
    @JsonCreator
    static JsonMain newInstance(@JsonProperty("temp") final Float temperature) {
        return new AutoValue_JsonMain(temperature);
    }

    public abstract Float getTemperature();
}
