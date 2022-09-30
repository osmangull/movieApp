package com.sanalkasif.movieapp.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Functions {

    fun String.getYearFromDateString() :String{
        val date = stringToDate(this)
        return getYearFromDate(date)
    }
    fun String.getDateFromDateString() :String{
        val date = stringToDate(this)
        return getDateWithDots(date)
    }

    fun getWithFormat(date: Date?, format: String?): String {
        val dateFormat = SimpleDateFormat(format, Locale.US)
        return dateFormat.format(date)
    }

    fun stringToDate(date: String): Date? {
        val df = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        return try {
            df.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
            null
        }
    }

    fun getYearFromDate(date: Date?): String {
        return (getWithFormat(date, "yyyy"))
    }

    fun getDateWithDots(date: Date?): String {
        return (getWithFormat(date, "dd") + "."
                + getWithFormat(date, "MM") + "."
                + getWithFormat(date, "yyyy"))
    }


}