package com.example.weatherapi_assignment.view

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapi_assignment.common.DateUtils
import com.example.weatherapi_assignment.common.WeatherCollectionUtils
import com.example.weatherapi_assignment.databinding.ForecastDetailsLayoutBinding
import com.example.weatherapi_assignment.model.*
import com.squareup.picasso.Picasso
import java.sql.Timestamp
import java.util.*

private const val TAG = "ForecastFragment"
class ForecastFragment : Fragment() {

    private var forecastDate = mutableMapOf<WeatherInfo,String>()
    private var uniqueDates = mutableListOf<WeatherInfo>()
    private lateinit var currentDate : WeatherInfo

    companion object{
        const val FORECAST_VAL = "Forecast_Info"
        const val STATE_NAME = "STATE"

        fun newInstance(stateName : StateInfo, forecastInformation : WeatherObject) : ForecastFragment{
            return ForecastFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(FORECAST_VAL, forecastInformation)
                    putParcelable(STATE_NAME, stateName)
                }
            }
        }
    }

    private lateinit var binding: ForecastDetailsLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = ForecastDetailsLayoutBinding.inflate(inflater, container,false)
        arguments?.let {  bundle ->
            bundle.getParcelable<WeatherObject>(FORECAST_VAL)?.let {
                initView(it)
            }
            bundle.getParcelable<StateInfo>(STATE_NAME)?.let {
                binding.tvStateCountry.text = "${it.state}, ${it.country}"
            }
        }


        return binding.root
    }

    private fun initView(dataset : WeatherObject){
        binding.rvForecastOverview.layoutManager = GridLayoutManager(context, 3)

        getSingleDatesFromResult(dataset)

        currentDate.let {
            displayFirstElementData(it)
        }

        binding.rvForecastOverview.adapter = ForecastItemAdapter(uniqueDates)
    }

    private fun displayFirstElementData(uniqueDate: WeatherInfo) {
        val firstElement : WeatherInfo = uniqueDate
        binding.tvTodayTemp.text = firstElement.main.temp.toString()
        Picasso.get().load("$PRE_IMG${uniqueDate.weather[0].icon}$POST_IMG").into(binding.ivTodayWeather)

        if(uniqueDate.equals(WeatherCollectionUtils.lowest)){
            binding.cvTodayForecast.setCardBackgroundColor(Color.CYAN)
        }else if(uniqueDate.equals(WeatherCollectionUtils.highest)){
            binding.cvTodayForecast.setCardBackgroundColor(Color.rgb(242,168,58))
        }
    }

    private fun getSingleDatesFromResult(dataset: WeatherObject) {
        var dateToAdd : String
            dataset.list.map { currentWeather ->
                dateToAdd = DateUtils().getCurrentDate(currentWeather.dt)
                if (!forecastDate.containsValue(dateToAdd)) {
                    if (!this::currentDate.isInitialized) {
                        currentDate = currentWeather
                        forecastDate.put(currentWeather, dateToAdd)
                    } else {
                        forecastDate.put(currentWeather, dateToAdd)
                        uniqueDates.add(currentWeather)
                    }
                }
            }
        WeatherCollectionUtils.sortForecastLtoH(uniqueDates, currentDate)
        }
    }