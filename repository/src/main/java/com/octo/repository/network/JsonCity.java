package com.octo.repository.network;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class JsonCity {
    @JsonCreator
    static JsonCity newInstance(@JsonProperty("name") final String name) {
        return new AutoValue_JsonCity(name);
    }

    public abstract String getName();
}
