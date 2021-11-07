package com.octo.androidweather;

import static java.util.concurrent.Executors.newSingleThreadExecutor;

import com.nicolasmouchel.executordecorator.ImmutableExecutorDecorator;
import com.nicolasmouchel.executordecorator.WeakExecutorDecorator;
import com.octo.presentation.CityForecastPresenterImpl;
import com.octo.presentation.CityForecastView;
import com.octo.presentation.DateUtilsInjector;
import com.octo.usecases.CityForecastInteractor;
import com.octo.usecases.CityForecastPresenter;
import com.octo.usecases.CityForecastRepository;

public class CityForecastModule {
    private final CityForecastController controller;

    public CityForecastModule(final MainActivity activity) {
        final CityForecastPresenter outputPort = new CityForecastPresenterImpl(provideCityForecastView(activity), activity.getResources(), new DateUtilsInjector());
        final CityForecastRepository repository = new MockCityForecastRepository();
        final CityForecastInteractor interactor = new CityForecastInteractor(repository, outputPort);
        this.controller = provideCityForecastController(new CityForecastControllerImpl(interactor));
    }

    public CityForecastController getController() {
        return controller;
    }

    @ImmutableExecutorDecorator
    private CityForecastController provideCityForecastController(final CityForecastController controller) {
        return new CityForecastControllerDecorator(newSingleThreadExecutor(), controller);
    }

    @WeakExecutorDecorator
    private CityForecastView provideCityForecastView(final CityForecastView view) {
        return new CityForecastViewDecorator(new UiThreadExecutor(), view);
    }
}