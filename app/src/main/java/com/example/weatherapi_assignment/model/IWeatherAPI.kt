package com.example.weatherapi_assignment.model

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface IWeatherAPI {
    //Todo check how to append the country
    @GET(ZIP_END_POINT)
    fun getZipCode(@Query (ZIP_ATT) zip : String,
                   @Query (ID_ATT) appid : String = APP_ID) : Call<ZIPCodeObject>

    @GET(FORECAST_END_POINT)
    fun getUnitForecast(@Query (LAT_ATT) lat : String,
                    @Query (LON_ATT) lon : String,
                    @Query (UNITS) units : String,
                    @Query (ID_ATT) appid: String = APP_ID) : Call<WeatherObject>

    @GET(FORECAST_END_POINT)
    fun getKelvinForecast(@Query (LAT_ATT) lat : String,
                    @Query (LON_ATT) lon : String,
                    @Query (ID_ATT) appid: String = APP_ID) : Call<WeatherObject>


}
