package io.julius.juno.util;

import android.text.format.DateUtils;

import java.util.Date;

public class Utils {

    public static String getFormattedTime(Date date) {
        long now = new Date().getTime();
        return DateUtils.getRelativeTimeSpanString(date.getTime(), now, DateUtils.SECOND_IN_MILLIS).toString();
    }
}
