package com.octo.usecases;

public class StringUtils {
    private StringUtils() {
        // static class
    }

    public static boolean isNotBlank(String cityName) {
        return cityName != null && cityName.trim().length() != 0;
    }
}
