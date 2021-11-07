package com.octo.presentation;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

import android.content.res.Resources;

import com.octo.usecases.Forecast;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

public class CityForecastPresenterImplTest {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    @Mock
    private CityForecastView view;
    @Mock
    private Resources resources;
    @Mock
    private DateUtilsInjector dateUtilsInjector;
    private CityForecastPresenterImpl presenter;

    @Before
    public void setup() {
        Locale.setDefault(Locale.FRANCE);
        MockitoAnnotations.initMocks(this);
        given(resources.getString(R.string.empty_input)).willReturn("Veuillez renseigner une ville");
        given(resources.getString(R.string.unknown_city)).willReturn("Aucune ville ne correspond à votre recherche");
        given(resources.getString(R.string.unavailable_forecasts)).willReturn("Les prévisions de cette ville ne sont pas disponibles actuellement");
        given(resources.getString(R.string.city_forecast, "Paris")).willReturn("Les prévisions à Paris");
        given(resources.getString(R.string.temperature_label, "14")).willReturn("14°");
        given(resources.getString(R.string.temperature_label, "21")).willReturn("21°");
        presenter = new CityForecastPresenterImpl(view, resources, dateUtilsInjector);
    }

    @Test
    public void onEmptyInput() {
        presenter.onEmptyInput();

        assertMessageDisplayed("Veuillez renseigner une ville");
    }

    @Test
    public void onUnknownCity() {
        presenter.onUnknownCity();

        assertMessageDisplayed("Aucune ville ne correspond à votre recherche");
    }

    @Test
    public void onGenericException() {
        presenter.onGenericException();

        assertMessageDisplayed("Les prévisions de cette ville ne sont pas disponibles actuellement");
    }

    @Test
    public void onUnavailableForecasts() {
        presenter.onUnavailableForecasts();

        assertMessageDisplayed("Les prévisions de cette ville ne sont pas disponibles actuellement");
    }

    @Test
    public void onForecast() throws ParseException {
        final Forecast worstForecast = getMockForecast("2016-10-04 19:40:00", 14.45f);
        final Forecast bestForecast = getMockForecast("2016-10-06 09:00:00", 20.57f);
        given(dateUtilsInjector.isToday(Objects.requireNonNull(dateFormat.parse("2016-10-06 09:00:00")))).willReturn(false);
        given(dateUtilsInjector.isToday(Objects.requireNonNull(dateFormat.parse("2016-10-04 19:40:00")))).willReturn(false);

        presenter.onForecasts("Paris", worstForecast, bestForecast);

        then(view).should().displayViewModel(
            ForecastViewModel.builder()
                .setTitle("Les prévisions à Paris")
                .setWorstTemperatureLabel("14°")
                .setBestTemperatureLabel("21°")
                .setWorstForecastLabel("Mardi à 19h")
                .setBestForecastLabel("Jeudi à 9h")
                .build()
        );
    }

    @Test
    public void onForecast_WhenForecastIsToday() throws ParseException {
        final Forecast forecast = getMockForecast("2016-10-06 09:00:00", 14.45f);
        given(dateUtilsInjector.isToday(Objects.requireNonNull(dateFormat.parse("2016-10-06 09:00:00")))).willReturn(true);

        presenter.onForecasts("Paris", forecast, forecast);

        then(view).should().displayViewModel(
            ForecastViewModel.builder()
                .setTitle("Les prévisions à Paris")
                .setWorstTemperatureLabel("14°")
                .setBestTemperatureLabel("14°")
                .setWorstForecastLabel("Aujourd'hui à  9h")
                .setBestForecastLabel("Aujourd'hui à  9h")
                .build()
        );
    }

    private Forecast getMockForecast(String dateTime, float temperature) throws ParseException {
        final Forecast forecast = mock(Forecast.class);
        given(forecast.getDate()).willReturn(dateFormat.parse(dateTime));
        given(forecast.getTemperature()).willReturn(temperature);
        return forecast;
    }

    private void assertMessageDisplayed(String message) {
        then(view).should().displayMessage(message);
    }

}