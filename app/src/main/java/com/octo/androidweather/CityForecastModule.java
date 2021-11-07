package com.octo.androidweather;

import static com.octo.androidweather.BuildConfig.WEATHER_BASE_URL;
import static java.util.concurrent.Executors.newSingleThreadExecutor;

import com.nicolasmouchel.executordecorator.ImmutableExecutorDecorator;
import com.nicolasmouchel.executordecorator.WeakExecutorDecorator;
import com.octo.presentation.CityForecastPresenterImpl;
import com.octo.presentation.CityForecastView;
import com.octo.presentation.DateUtilsInjector;
import com.octo.repository.network.NetworkModule;
import com.octo.repository.network.WeatherNetworkRepository;
import com.octo.usecases.CityForecastInteractor;
import com.octo.usecases.CityForecastPresenter;
import com.octo.usecases.CityForecastRepository;

import okhttp3.HttpUrl;
import retrofit2.Retrofit;

public class CityForecastModule {
    private final CityForecastController controller;

    public CityForecastModule(final MainActivity activity) {
        final CityForecastPresenter presenter = new CityForecastPresenterImpl(
            provideCityForecastView(activity),
            activity.getResources(),
            new DateUtilsInjector()
        );

        final Retrofit retrofit = new NetworkModule().getRetrofit(HttpUrl.parse(WEATHER_BASE_URL));
        final CityForecastRepository repository = new WeatherNetworkRepository(retrofit);
        final CityForecastInteractor interactor = new CityForecastInteractor(repository, presenter);
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
