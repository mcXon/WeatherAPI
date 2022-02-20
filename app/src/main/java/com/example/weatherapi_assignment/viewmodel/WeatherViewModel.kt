package com.example.weatherapi_assignment.viewmodel

import androidx.lifecycle.ViewModel
import com.example.weatherapi_assignment.common.Network
import com.example.weatherapi_assignment.model.WeatherObject
import com.example.weatherapi_assignment.model.ZIPCodeObject
import retrofit2.Call

class WeatherViewModel : ViewModel(){

    fun getZipCodes(zipCode : String) : Call<ZIPCodeObject> {
        return Network.ZipCodeAPI.getZipCode(zipCode)
    }

    fun getKelvinWeatherForecast(lat : String, lon : String) : Call<WeatherObject>{
        return Network.ZipCodeAPI.getKelvinForecast(lat,lon)
    }

    fun getUnitWeatherForecast(lat : String, lon : String, unit : String) : Call<WeatherObject>{
        return Network.ZipCodeAPI.getUnitForecast(lat,lon,unit)
    }
}