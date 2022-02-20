package com.example.weatherapi_assignment.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapi_assignment.databinding.ForecastItemBinding
import com.example.weatherapi_assignment.model.WeatherInfo
import com.example.weatherapi_assignment.model.WeatherObject

class ForecastItemAdapter(
    private val forecastDate: MutableList<WeatherInfo>
) : RecyclerView.Adapter<ForecastViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder =
        ForecastViewHolder(ForecastItemBinding.inflate(LayoutInflater.from(parent.context), parent,false))


    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.onBind(forecastDate[position])
    }

    override fun getItemCount(): Int {
        return forecastDate.size
    }
}