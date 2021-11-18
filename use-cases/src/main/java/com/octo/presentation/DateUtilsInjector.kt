package com.octo.presentation

import android.text.format.DateUtils
import java.util.*
import javax.inject.Inject

class DateUtilsInjector @Inject constructor() {
    fun isToday(date: Date): Boolean = DateUtils.isToday(date.time)
}