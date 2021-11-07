package com.octo.repository.network;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import java.io.IOException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;

public class WeatherNetworkRepositoryTest {
    @Rule
    public MockWebServer server = new MockWebServer();
    private WeatherNetworkRepository repository;

    @Before
    public void setup() {
        final NetworkModule networkModule = new NetworkModule();
        final Retrofit retrofit = networkModule.getRetrofit(server.url("/"));
        repository = new WeatherNetworkRepository(retrofit);
    }

    @Test
    public void loadWeatherAsString() throws IOException {
        final MockResponse response = new MockResponse()
            .setResponseCode(200)
            .setBody(FileUtils.read("forecast.json"));
        server.enqueue(response);

        String weather = repository.loadWeatherAsString("paris");

        assertThat(weather).isNotEmpty();
    }
}