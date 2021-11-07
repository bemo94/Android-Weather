package com.octo.androidweather;

import com.octo.presentation.CityForecastPresenterImpl;
import com.octo.presentation.DateUtilsInjector;
import com.octo.usecases.CityForecastInteractor;
import com.octo.usecases.CityForecastPresenter;
import com.octo.usecases.CityForecastRepository;

public class CityForecastModule {
    private final CityForecastController controller;

    public CityForecastModule(final MainActivity activity) {
        final CityForecastPresenter outputPort = new CityForecastPresenterImpl(activity, activity.getResources(), new DateUtilsInjector());
        final CityForecastRepository repository = new MockCityForecastRepository();
        final CityForecastInteractor interactor = new CityForecastInteractor(repository, outputPort);
        this.controller = new CityForecastController(interactor);
    }

    public CityForecastController getController() {
        return controller;
    }
}
