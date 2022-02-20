package com.example.weatherapi_assignment.common

import java.sql.Timestamp
import java.util.*

class DateUtils {

    fun getCurrentDate(timeMillis : Long) : String{
        val timeStamp = Timestamp(timeMillis * 1000)
        val date = Date(timeStamp.time)
        return (date.month + 1).toString() + "/" + date.date.toString()
    }
}