package com.example.weatherapi_assignment.common

import android.util.Log
import com.example.weatherapi_assignment.model.WeatherInfo

private const val TAG = "WeatherCollectionUtils"

object WeatherCollectionUtils {
    lateinit var highest : WeatherInfo
    lateinit var lowest : WeatherInfo

    fun sortForecastLtoH( forecastDate: MutableList<WeatherInfo>, currentDate : WeatherInfo){
        val cWeather = forecastDate.toMutableList()
        cWeather.add(currentDate)
        cWeather.sortWith(
                kotlin.Comparator{ x, y -> x.main.temp.compareTo(y.main.temp) }
            )
        lowest = cWeather[0]
        highest = cWeather[cWeather.lastIndex-1]
        Log.d(TAG, "getSingleDatesFromResult: lowest : ${lowest}, highest ${highest}")
    }
}