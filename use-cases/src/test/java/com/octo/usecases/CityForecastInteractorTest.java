package com.octo.usecases;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import de.bechte.junit.runners.context.HierarchicalContextRunner;

import com.octo.usecases.CityForecastRepository.GenericException;
import com.octo.usecases.CityForecastRepository.UnknownCityException;

import static java.util.Arrays.asList;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(HierarchicalContextRunner.class)
public class CityForecastInteractorTest {
    @InjectMocks
    private CityForecastInteractor interactor;
    @Mock
    private CityForecastRepository repository;
    @Mock
    private CityForecastPresenter presenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
        verifyNoMoreInteractions(presenter);
    }

    public class InvalidCityName {
        @Test
        public void loadCityForecast_WhenCityNameIsNull() {
            interactor.loadCityForecast(null);

            then(presenter).should().onEmptyInput();
        }

        @Test
        public void loadCityForecast_WhenCityNameIsEmpty() {
            interactor.loadCityForecast("");

            then(presenter).should().onEmptyInput();
        }

        @Test
        public void loadCityForecast_WhenCityNameIsBlank() {
            interactor.loadCityForecast("  ");

            then(presenter).should().onEmptyInput();
        }
    }

    public class ValidCityName {
        public class NotExistingTown {
            @Test
            public void loadCityForecast() throws UnknownCityException, GenericException {
                final String cityName = "unknown";
                doThrow(UnknownCityException.class).when(repository).loadCityForecast(cityName);

                interactor.loadCityForecast(cityName);

                then(presenter).should().onUnknownCity();
            }
        }

        public class ExistingTown {
            private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
            private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

            private static final String CITY = "city";
            @Mock
            private CityWeeklyForecast cityForecast;

            @Before
            public void setup() {
                MockitoAnnotations.initMocks(this);
            }

            @Test
            public void loadCityForecast_WhenGenericException() throws UnknownCityException, GenericException {
                doThrow(GenericException.class).when(repository).loadCityForecast(CITY);

                interactor.loadCityForecast(CITY);

                then(presenter).should().onGenericException();
            }

            @Test
            public void loadCityName_WhenNoForecastAreAvailable() throws UnknownCityException, GenericException {
                given(cityForecast.getForecasts()).willReturn(Collections.<Forecast>emptyList());
                given(repository.loadCityForecast(CITY)).willReturn(cityForecast);

                interactor.loadCityForecast(CITY);

                then(presenter).should().onUnavailableForecasts();
            }

            @Test
            public void loadCityName_WhenLessThanTwoRelevantForecasts() throws Exception {
                final List<Forecast> forecasts = asList(
                    getMockForecast("2017-10-01 21:30:00"),
                    getMockForecast("2017-10-01 18:00:00"),
                    getMockForecast("2017-10-01 07:30:00")
                );
                given(cityForecast.getForecasts()).willReturn(forecasts);
                given(repository.loadCityForecast(CITY)).willReturn(cityForecast);

                interactor.loadCityForecast(CITY);

                then(presenter).should().onUnavailableForecasts();
            }

            @Test
            public void loadCityName_WhenForecastsAreRelevant() throws Exception {
                final Forecast worstForecast = getMockForecast("2017-10-01 19:40:00", 14f);
                final Forecast bestForecast = getMockForecast("2017-10-01 16:00:00", 20f);
                final Forecast unrelevantForecast = getMockForecast("2017-10-01 07:30:00", 12f);
                final List<Forecast> forecasts = asList(worstForecast, bestForecast, unrelevantForecast);
                given(cityForecast.getForecasts()).willReturn(forecasts);
                given(repository.loadCityForecast(CITY)).willReturn(cityForecast);

                interactor.loadCityForecast(CITY);

                then(presenter).should().onForecasts(worstForecast, bestForecast);
            }

            private Forecast getMockForecast(String dateTime, float temperature) throws ParseException {
                final Forecast forecast = mock(Forecast.class);
                given(forecast.getDate()).willReturn(dateFormat.parse(dateTime));
                given(forecast.getTemperature()).willReturn(temperature);
                return forecast;
            }

            private Forecast getMockForecast(String dateTime) throws ParseException {
                final Forecast forecast = mock(Forecast.class);
                given(forecast.getDate()).willReturn(dateFormat.parse(dateTime));
                return forecast;
            }
        }
    }
}