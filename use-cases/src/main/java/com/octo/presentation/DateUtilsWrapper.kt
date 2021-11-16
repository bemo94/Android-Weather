package com.octo.presentation

import android.text.format.DateUtils
import java.util.*

class DateUtilsWrapper {
    fun isToday(date: Date): Boolean = DateUtils.isToday(date.time)
}