package com.octo.repository.network;

import static org.assertj.core.api.Assertions.assertThat;

import com.octo.repository.CityWeeklyForecast;
import com.octo.repository.Forecast;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;

public class WeatherNetworkRepositoryTest {
    @Rule public MockWebServer server = new MockWebServer();
    private WeatherNetworkRepository repository;
    private NetworkModule networkModule;

    @Before
    public void setup() {
        networkModule = new NetworkModule();
        final Retrofit retrofit = networkModule.getRetrofit(server.url("/"));
        repository = new WeatherNetworkRepository(retrofit);
    }

    @Test
    public void loadWeatherAsString() throws IOException {
        final MockResponse response = new MockResponse()
            .setResponseCode(200)
            .setBody(FileUtils.read("forecast.json"));
        server.enqueue(response);

        final String weather = repository.loadWeatherAsString("paris");

        assertThat(weather).isNotEmpty();
    }

    @Test
    public void loadCityWeeklyForecast() throws IOException, ParseException {
        // Given
        final MockResponse response = new MockResponse()
            .setResponseCode(200)
            .setBody(FileUtils.read("forecast.json"));
        server.enqueue(response);

        // When
        final CityWeeklyForecast weeklyForecast = repository.loadCityWeeklyForecast("paris");

        // Then
        assertThat(weeklyForecast).isNotNull();
        assertThat(weeklyForecast.getCityName()).isEqualToIgnoringCase("Paris");

        final List<Forecast> forecasts = weeklyForecast.getForecasts();
        assertThat(forecasts).isNotEmpty();

        final Forecast forecast = forecasts.get(0);
        assertThat(forecast.getTemperature()).isEqualTo(14.76f);

        final Date expectedDate = networkModule.getDateFormat().parse("2017-10-01 18:00:00");
        assertThat(forecasts.get(0).getDate()).isEqualTo(expectedDate);
    }
}