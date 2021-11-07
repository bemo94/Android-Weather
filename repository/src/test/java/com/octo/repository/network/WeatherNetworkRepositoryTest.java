package com.octo.repository.network;

import static org.assertj.core.api.Assertions.assertThat;
import static okhttp3.mockwebserver.SocketPolicy.*;
import com.octo.repository.transform.AutoCityWeeklyForecast;
import com.octo.repository.transform.AutoForecast;
import com.octo.usecases.CityForecastRepository;
import com.octo.usecases.CityWeeklyForecast;
import com.octo.usecases.Forecast;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import java.util.ArrayList;
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
    public void loadCityForecast() throws Exception {
        final MockResponse response = new MockResponse()
            .setResponseCode(200)
            .setBody(FileUtils.read("forecast.json"));
        server.enqueue(response);

        final CityWeeklyForecast weeklyForecast = repository.loadCityForecast("paris");

        final Date expectedDate = networkModule.getDateFormat().parse("2017-10-01 18:00:00");
        final Forecast expectedForecast = AutoForecast.builder().setTemperature(14.76f).setDate(expectedDate).build();
        final List<Forecast> forecastList = new ArrayList<>();
        forecastList.add(expectedForecast);
        final CityWeeklyForecast expectedResult = AutoCityWeeklyForecast.builder()
            .setCityName("Paris")
            .setForecasts(forecastList)
            .build();

        assertThat(weeklyForecast).isEqualTo(expectedResult);
    }

    @Test(expected = CityForecastRepository.GenericException.class)
    public void loadCityForecast_WhenGenericException() throws Exception {
        server.enqueue(new MockResponse().setSocketPolicy(DISCONNECT_AT_START));

        repository.loadCityForecast("paris");
    }

    @Test(expected = CityForecastRepository.UnknownCityException.class)
    public void loadCityForecast_WhenUnknownCityException() throws Exception {
        server.enqueue(new MockResponse().setResponseCode(404));

        repository.loadCityForecast("paris");
    }

    @Test(expected = CityForecastRepository.GenericException.class)
    public void loadCityForecast_WhenResponseCodeNotHandled() throws Exception {
        server.enqueue(new MockResponse().setResponseCode(500));

        repository.loadCityForecast("paris");
    }
}