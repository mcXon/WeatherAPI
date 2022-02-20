package com.example.weatherapi_assignment.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


/**
 * todo review how to get UNIX Timestamp 1645153200
 * https://stackoverflow.com/questions/47250263/kotlin-convert-timestamp-to-datetime
 */
@Parcelize
data class WeatherObject(
    val list : List<WeatherInfo>
) : Parcelable

@Parcelize
data class WeatherInfo(
    val dt : Long,
    val main : TempInfo,
    val weather : List<WeatherData>,
    val dt_txt : String,
    val isHigher: Boolean,
    val isLower : Boolean
) : Parcelable

@Parcelize
data class TempInfo(
    val temp : Double
) : Parcelable

@Parcelize
data class StateInfo(
    val state : String,
    val country : String
) : Parcelable

@Parcelize
data class WeatherData(
    val icon : String
) : Parcelable