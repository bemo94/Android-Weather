package com.octo.presentation;

import android.text.format.DateUtils;

import java.util.Date;

public class DateUtilsInjector {

    Boolean isToday(Date date) {
        return DateUtils.isToday(date.getTime());
    }

}
