package com.example.weatherapi_assignment.common

import com.example.weatherapi_assignment.model.BASE_URL
import com.example.weatherapi_assignment.model.IWeatherAPI
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object Network {

    val ZipCodeAPI : IWeatherAPI by lazy {
        initRetrofit().create(IWeatherAPI::class.java)
    }

    private fun initRetrofit(): Retrofit{
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
}