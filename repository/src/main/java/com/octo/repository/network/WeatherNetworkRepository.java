package com.octo.repository.network;

import retrofit2.Retrofit;

public class WeatherNetworkRepository {
    private static final String API_KEY = "a72ffd88bc68442d7d91981af6832ca1";

    private final Retrofit retrofit;

    public WeatherNetworkRepository(final Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public String loadWeatherAsString(final String town) {
        return null;
    }
}
