package ru.kpfu.itis.stayintouch.utils

import java.util.*

class DateHelper {

    companion object {

        fun parseDate(date: GregorianCalendar): String {
            val hour = if (date.get(Calendar.HOUR_OF_DAY) + 3 > 23) {
                date.get(Calendar.HOUR_OF_DAY) + 3 - 24
            } else {
                date.get(Calendar.HOUR_OF_DAY) + 3
            }
            val hourString = if (hour < 10) {
                "0$hour"
            } else {
                "$hour"
            }
            val minute = if (date.get(Calendar.MINUTE) < 10) {
                "0${date.get(Calendar.MINUTE)}"
            } else {
                "${date.get(Calendar.MINUTE)}"
            }
            val dateText =
                "$hourString:$minute ${
                date.get(Calendar.DAY_OF_MONTH)}.${
                date.get(Calendar.MONTH).plus(1)}.${
                date.get(Calendar.YEAR)}"
            return dateText
        }
    }
}