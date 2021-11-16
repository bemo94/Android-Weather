package com.octo.usecases

object StringUtils {
    fun isBlank(cityName: String): Boolean {
        return cityName.trim { it <= ' ' }.isEmpty()
    }
}