package com.octo.repository.network;

import com.octo.repository.CityWeeklyForecast;
import com.octo.repository.transform.ForecastTransformer;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class WeatherNetworkRepository {
    interface WeatherService {
        @GET("forecast")
        Call<String> getWeatherAsString(
            @Query("q") final String town,
            @Query("appId") final String apiKey,
            @Query("units") final String units
        );

        @GET("forecast")
        Call<JsonCityWeeklyForecast> getCityWeeklyForecast(
            @Query("q") final String town,
            @Query("appId") final String apiKey,
            @Query("units") final String units
        );
    }

    private static final String API_KEY = "a72ffd88bc68442d7d91981af6832ca1";
    private static final String UNITS = "metric";

    private final Retrofit retrofit;

    public WeatherNetworkRepository(final Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public String loadWeatherAsString(final String town) {
        try {
            final Response<String> response = retrofit.create(WeatherService.class)
                .getWeatherAsString(town, API_KEY, UNITS)
                .execute();
            if (response.isSuccessful()) {
                return response.body();
            }
        } catch (IOException e) {
            System.err.print(e);
        }
        return null;
    }

    public CityWeeklyForecast loadCityWeeklyForecast(final String town) {
        try {
            final Response<JsonCityWeeklyForecast> response = retrofit.create(WeatherService.class)
                .getCityWeeklyForecast(town, API_KEY, UNITS)
                .execute();

            if (response.isSuccessful() && response.body() != null) {
                final ForecastTransformer transformer = new ForecastTransformer();
                return transformer.toCityWeeklyForecast(response.body());
            }
        } catch (IOException e) {
            System.err.print(e);
        }
        return null;
    }
}
