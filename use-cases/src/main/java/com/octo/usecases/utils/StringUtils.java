package com.octo.usecases.utils;

public class StringUtils {
    private StringUtils() {
        // static class
    }

    public static boolean isBlank(String cityName) {
        return cityName == null || cityName.trim().length() == 0;
    }
}
