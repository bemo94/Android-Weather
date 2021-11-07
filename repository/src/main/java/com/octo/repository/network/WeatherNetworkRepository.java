package com.octo.repository.network;

import com.octo.repository.transform.ForecastTransformer;
import com.octo.usecases.CityForecastRepository;
import com.octo.usecases.CityWeeklyForecast;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class WeatherNetworkRepository implements CityForecastRepository {
    public interface WeatherService {
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

    @Override
    public CityWeeklyForecast loadCityForecast(final String town) throws CityForecastRepository.GenericException, UnknownCityException {
        try {
            final Response<JsonCityWeeklyForecast> response = retrofit.create(WeatherService.class)
                .getCityWeeklyForecast(town, API_KEY, UNITS)
                .execute();

            if (response.isSuccessful()) {
                return new ForecastTransformer().toCityWeeklyForecast(response.body());
            } else if (response.code() == 404) {
                throw new UnknownCityException();
            }
        } catch (IOException ignored) {

        }
        throw new GenericException();
    }
}
