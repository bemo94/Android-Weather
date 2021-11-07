package com.octo.usecases;

import com.octo.usecases.utils.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CityForecastInteractor {
    private static final int MINIMUM_RELEVANT_HOUR = 8;
    private static final int MAXIMUM_RELEVANT_HOUR = 20;
    private static final int REQUIRED_FORECASTS = 2;

    private final CityForecastRepository repository;
    private final CityForecastPresenter presenter;

    public CityForecastInteractor(final CityForecastRepository repository,
                                  final CityForecastPresenter presenter) {
        this.repository = repository;
        this.presenter = presenter;
    }

    public void loadCityForecast(final String cityName) {
        if (StringUtils.isBlank(cityName)) {
            presenter.onEmptyInput();
            return;
        }
        handleValidCityName(cityName);
    }

    private void handleValidCityName(String cityName) {
        try {
            handleRetrievedForecast(repository.loadCityForecast(cityName));
        } catch (CityForecastRepository.GenericException e) {
            presenter.onGenericException();
        } catch (CityForecastRepository.UnknownCityException e) {
            presenter.onUnknownCity();
        }
    }

    private void handleRetrievedForecast(CityWeeklyForecast cityForecast) {
        final List<Forecast> relevantForecasts = getRelevantForecasts(cityForecast);
        if (relevantForecasts.size() < REQUIRED_FORECASTS) {
            presenter.onUnavailableForecasts();
            return;
        }
        sortForecastsByAscendingTemperature(relevantForecasts);
        presenter.onForecasts(cityForecast.getCityName(), worstForecast(relevantForecasts), bestForecast(relevantForecasts));
    }

    private List<Forecast> getRelevantForecasts(CityWeeklyForecast cityForecast) {
        final List<Forecast> relevantForecasts = new ArrayList<>();
        for (Forecast forecast : cityForecast.getForecasts()) {
            if (isRelevant(forecast)) {
                relevantForecasts.add(forecast);
            }
        }
        return relevantForecasts;
    }

    private void sortForecastsByAscendingTemperature(List<Forecast> relevantForecasts) {
        Collections.sort(relevantForecasts, new Comparator<Forecast>() {
            @Override
            public int compare(Forecast f1, Forecast f2) {
                return f1.getTemperature().compareTo(f2.getTemperature());
            }
        });
    }

    private Forecast bestForecast(List<Forecast> sortedForecasts) {
        return sortedForecasts.get(sortedForecasts.size() - 1);
    }

    private Forecast worstForecast(List<Forecast> sortedForecasts) {
        return sortedForecasts.get(0);
    }

    private boolean isRelevant(Forecast forecast) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(forecast.getDate());
        final int hours = calendar.get(Calendar.HOUR_OF_DAY);
        return hours >= MINIMUM_RELEVANT_HOUR && hours <= MAXIMUM_RELEVANT_HOUR;
    }
}
