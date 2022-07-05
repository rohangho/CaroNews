package com.example.caronews.utils

import android.text.format.DateUtils
import java.lang.Math.abs

object TimeLogic{

    fun getAgoFromTimeStamp(timeInMilli:Long): String? {
        var mutableTimeMilli = timeInMilli
        var SECOND_MILLIS:Long = 1000;
        var MINUTE_MILLIS:Long = 60 * SECOND_MILLIS;
        var HOUR_MILLIS:Long = 60 * MINUTE_MILLIS;
        var DAY_MILLIS:Long = 24 * HOUR_MILLIS;
        var MONTH_MILLIS:Long = kotlin.math.abs(30 * DAY_MILLIS)
        var YEAR_MILLI :Long = (DAY_MILLIS * 365)


        if (mutableTimeMilli < 1000000000000L) {
            mutableTimeMilli *= 1000
        }

        val now = System.currentTimeMillis()
        if (mutableTimeMilli > now || mutableTimeMilli <= 0) {
            return null
        }


        val diff: Long = System.currentTimeMillis() - mutableTimeMilli
        return if (diff < MINUTE_MILLIS) {
            "just now"
        } else if (diff < 2 * MINUTE_MILLIS) {
            "a minute ago"
        } else if (diff < 50 * MINUTE_MILLIS) {
            diff.div(MINUTE_MILLIS).toString() + " minutes ago"
        } else if (diff < 90 * MINUTE_MILLIS) {
            "an hour ago"

        } else if (diff < 24 * HOUR_MILLIS) {
            diff.div(HOUR_MILLIS).toString() + " hours ago"
        } else if (diff < 48 * HOUR_MILLIS) {
            "yesterday"
        } else if(diff< 30 * DAY_MILLIS) {
            diff.div(DAY_MILLIS).toString() + " days ago"
        }else if(diff< 12*MONTH_MILLIS) {
            diff.div(MONTH_MILLIS).toString() + "months ago"
        }else
            diff.div(YEAR_MILLI).toString() + " years ago"
    }

}