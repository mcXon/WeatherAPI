package com.example.weatherapi_assignment.view

import android.content.res.Resources
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapi_assignment.common.DateUtils
import com.example.weatherapi_assignment.common.WeatherCollectionUtils
import com.example.weatherapi_assignment.databinding.ForecastItemBinding
import com.example.weatherapi_assignment.model.POST_IMG
import com.example.weatherapi_assignment.model.PRE_IMG
import com.example.weatherapi_assignment.model.WeatherInfo
import com.squareup.picasso.Picasso

class ForecastViewHolder(private val binding: ForecastItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(weatherInfo: WeatherInfo){
        Picasso.get().load("$PRE_IMG${weatherInfo.weather[0].icon}$POST_IMG").into(binding.ivWeatherIcon)
        binding.tvDate.text = DateUtils().getCurrentDate(weatherInfo.dt)
        binding.tvTempData.text = weatherInfo.main.temp.toString()
        if(weatherInfo.equals(WeatherCollectionUtils.lowest)){
            binding.cvItem.setCardBackgroundColor(Color.CYAN)
        }else if(weatherInfo.equals(WeatherCollectionUtils.highest)){
            binding.cvItem.setCardBackgroundColor(Color.rgb(242,168,58))
        }
    }
}