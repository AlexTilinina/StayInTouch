package ru.kpfu.itis.stayintouch.utils

import java.text.SimpleDateFormat
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
            val day = if (date.get(Calendar.DAY_OF_MONTH) < 10) {
                "0${date.get(Calendar.DAY_OF_MONTH)}"
            } else {
                "${date.get(Calendar.DAY_OF_MONTH)}"
            }
            val month = if (date.get(Calendar.MONTH) + 1 < 10) {
                "0${date.get(Calendar.MONTH) + 1}"
            } else {
                "${date.get(Calendar.MONTH) + 1}"
            }
            val dateText =
                "$hourString:$minute $day.$month.${
                date.get(Calendar.YEAR)}"
            return dateText
        }

        fun getDateCreated(stringDate: String?) : GregorianCalendar {
            val format = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSSSS")
            val date = format.parse(stringDate)
            val dateCreated = GregorianCalendar()
            dateCreated.time = date
            return dateCreated
        }
    }
}