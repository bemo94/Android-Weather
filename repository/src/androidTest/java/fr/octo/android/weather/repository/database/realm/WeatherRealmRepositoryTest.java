package fr.octo.android.weather.repository.database.realm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.octo.repository.realm.WeatherRealmRepository;
import com.octo.repository.transform.AutoCityWeeklyForecast;
import com.octo.repository.transform.AutoForecast;
import com.octo.usecases.CityWeeklyForecast;

@RunWith(AndroidJUnit4.class)
public class WeatherRealmRepositoryTest {
    private Realm realm;
    private WeatherRealmRepository repository;

    @Before
    public void setUp() {
        Realm.init(InstrumentationRegistry.getTargetContext());
        realm = Realm.getInstance(new RealmConfiguration.Builder().inMemory().build());
        repository = new WeatherRealmRepository(realm);
    }

    @After
    public void tearDown() {
        realm.close();
    }

    @Test
    public void integrationTest() {
        // Given
        final String city = "Paris";
        final float temperature = 12f;
        final Date date = new Date(0);
        final CityWeeklyForecast cityForecast = getCityWeeklyForecast(city, temperature, date);

        // When
        repository.writeCityWeeklyForecast(cityForecast);
        final CityWeeklyForecast retrievedCityForecast = repository.loadCityWeeklyForecast(city);

        // Then
        assertNotNull(retrievedCityForecast);
        assertEquals(retrievedCityForecast.getCityName(), city);
        assertEquals(retrievedCityForecast.getForecasts().size(), 1);
        assertEquals(retrievedCityForecast.getForecasts().get(0).getTemperature(), temperature, 0f);
        assertEquals(retrievedCityForecast.getForecasts().get(0).getDate(), date);
    }

    @Test
    public void writeCityWeeklyForecast_ShouldRemovePreviousCityWeeklyForecast() {
        // Given
        final String city = "Paris";
        final Date date = new Date(0);
        final float temperature1 = 12f;
        final float temperature2 = 30f;
        final CityWeeklyForecast cityForecast1 = getCityWeeklyForecast(city, temperature1, date);
        final CityWeeklyForecast cityForecast2 = getCityWeeklyForecast(city, temperature2, date);

        // When
        repository.writeCityWeeklyForecast(cityForecast1);
        repository.writeCityWeeklyForecast(cityForecast2);
        final CityWeeklyForecast retrievedCityForecast = repository.loadCityWeeklyForecast(city);

        // Then
        assertNotNull(retrievedCityForecast);
        assertEquals(retrievedCityForecast.getCityName(), city);
        assertEquals(retrievedCityForecast.getForecasts().size(), 1);
        assertEquals(retrievedCityForecast.getForecasts().get(0).getTemperature(), temperature2, 0f);
    }

    private AutoCityWeeklyForecast getCityWeeklyForecast(String city, float temperature, Date date) {
        return AutoCityWeeklyForecast.builder()
            .setCityName(city)
            .setForecasts(Collections.singletonList(
                AutoForecast.builder()
                    .setDate(date)
                    .setTemperature(temperature)
                    .build()))
            .build();
    }
}
