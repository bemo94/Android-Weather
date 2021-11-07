package com.octo.androidweather;

import static org.mockito.BDDMockito.then;

import com.octo.usecases.CityForecastInteractor;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class CityForecastControllerImplTest {
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    @InjectMocks
    private CityForecastControllerImpl controller;
    @Mock
    private CityForecastInteractor interactor;

    @Test
    public void loadCityForecast_WhenArgumentIsNull() {
        controller.loadCityForecast(null);

        then(interactor).should().loadCityForecast(null);
    }

    @Test
    public void loadCityForecast_ShouldForwardToInteractor() {
        controller.loadCityForecast("city");

        then(interactor).should().loadCityForecast("city");
    }

    @Test
    public void loadCityForecast_ShouldTrimArgument() {
        controller.loadCityForecast("   city  ");

        then(interactor).should().loadCityForecast("city");
    }
}