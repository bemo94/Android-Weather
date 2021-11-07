package com.octo.androidweather;

import static android.content.ContentValues.TAG;
import static java.util.Arrays.asList;

import android.util.Log;

import com.octo.usecases.CityForecastRepository;
import com.octo.usecases.CityWeeklyForecast;
import com.octo.usecases.Forecast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MockCityForecastRepository implements CityForecastRepository {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

    @Override
    public CityWeeklyForecast loadCityForecast(String cityName) throws GenericException, UnknownCityException {
        if ("GENERIC".equalsIgnoreCase(cityName)) {
            throw new GenericException();
        }

        if ("UNKNOWN".equalsIgnoreCase(cityName)) {
            throw new UnknownCityException();
        }

        return new CityWeeklyForecast() {
            @Override
            public String getCityName() {
                return "Paris";
            }

            @Override
            public List<Forecast> getForecasts() {
                return asList(
                    new Forecast() {
                        @Override
                        public Float getTemperature() {
                            return 14.56f;
                        }

                        @Override
                        public Date getDate() {
                            return parseDateQuielty("2017-10-06 19:40:00");
                        }
                    },
                    new Forecast() {

                        @Override
                        public Float getTemperature() {
                            return 34.5658f;
                        }

                        @Override
                        public Date getDate() {
                            return parseDateQuielty("2017-10-08 14:40:00");
                        }
                    }
                );
            }
        };
    }

    private static Date parseDateQuielty(String source) {
        try {
            return dateFormat.parse(source);
        } catch (ParseException e) {
            Log.e(TAG, "Exception", e);
        }
        return new Date();
    }
}
