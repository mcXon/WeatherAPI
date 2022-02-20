package com.example.weatherapi_assignment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapi_assignment.databinding.ActivityMainBinding
import com.example.weatherapi_assignment.model.StateInfo
import com.example.weatherapi_assignment.model.WeatherObject
import com.example.weatherapi_assignment.model.ZIPCodeObject
import com.example.weatherapi_assignment.view.ForecastFragment
import com.example.weatherapi_assignment.viewmodel.WeatherViewModel
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "MainActivity"
private const val fahrenheit = "Fahrenheit"
private const val fahrenheit_unit = "imperial"
private const val celsius_unit = "metric"
private const val kelvin = "Kelvin"

class MainActivity : AppCompatActivity() {

    private val viewModel : WeatherViewModel by lazy {
        ViewModelProvider(this,
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return WeatherViewModel() as T
            }
        })[WeatherViewModel::class.java]
    }

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)

        val spinner = binding.spMeasurementTypes
        ArrayAdapter.createFromResource(
            this,R.array.temperature, android.R.layout.simple_spinner_item
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = it
        }

        binding.spMeasurementTypes
        binding.btnDisplayForecast.setOnClickListener {
            //implementation to hide keyboard
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(binding.root.windowToken, 0)
            executeForecastSearch()
        }

        setContentView(binding.root)
    }

    private fun inflateForecastFragment(stateName : StateInfo, dataset : WeatherObject?){
        dataset?.let {
            supportFragmentManager.beginTransaction().addToBackStack(null).replace(
                android.R.id.content, ForecastFragment.newInstance(stateName, it)
            ).commit()
        }
    }

    private fun executeForecastSearch(){
        binding.etZipcodeInput.let {
            if(it.text.isNotEmpty()){
                viewModel.getZipCodes("${it.text},us").enqueue(
                    object : Callback<ZIPCodeObject>{
                        override fun onResponse(
                            call: Call<ZIPCodeObject>,
                            response: Response<ZIPCodeObject>,
                        ) {
                            if(response.isSuccessful){
                                val zipObj : ZIPCodeObject = (response.body() as ZIPCodeObject)
                                Log.d(TAG, "onResponse: coord ${zipObj.coord.lat} , ${zipObj.coord.lon}")
                                binding.spMeasurementTypes.let {
                                    val selectedUnit = it.selectedItem.toString()
                                    if(selectedUnit.equals(kelvin)){
                                        performKelvinSearch(zipObj)
                                    }else{
                                        if(selectedUnit.equals(fahrenheit)){ //fahrenheit
                                            performUnitSearch(zipObj, fahrenheit_unit)
                                        }else{//celsius
                                            performUnitSearch(zipObj, celsius_unit)
                                        }
                                    }
                                }
                            }else{
                                Snackbar.make(binding.root,"Zip code must be inside the US",Snackbar.LENGTH_SHORT).show()
                            }

                        }
                        override fun onFailure(call: Call<ZIPCodeObject>, t: Throwable) {
                            Log.d(TAG, "onFailure: ${t.message}")
                        }
                    }
                )
            }
        }
    }

    private fun performUnitSearch(zipObj : ZIPCodeObject, metric : String){
            viewModel.getUnitWeatherForecast(zipObj.coord.lat, zipObj.coord.lon, metric).enqueue(
                object : Callback<WeatherObject>{
                    override fun onResponse(
                        call: Call<WeatherObject>,
                        response: Response<WeatherObject>,
                    ) {
                        var stateInfo = StateInfo(zipObj.name, zipObj.sys.country)
                        Log.d(TAG, "onResponseForecast: ${response.body()}")
                        inflateForecastFragment(stateInfo, response.body())
                    }

                    override fun onFailure(call: Call<WeatherObject>, t: Throwable) {
                        Log.d(TAG, "onResponseForecastFail: ${t.message}")
                    }
                }
            )

    }

    private fun performKelvinSearch(zipObj : ZIPCodeObject){
        viewModel.getKelvinWeatherForecast(zipObj.coord.lat, zipObj.coord.lon).enqueue(
            object : Callback<WeatherObject>{
                override fun onResponse(
                    call: Call<WeatherObject>,
                    response: Response<WeatherObject>,
                ) {
                    var stateInfo = StateInfo(zipObj.name, zipObj.sys.country)
                    Log.d(TAG, "onResponseForecast: ${response.body()}")
                    inflateForecastFragment(stateInfo, response.body())
                }

                override fun onFailure(call: Call<WeatherObject>, t: Throwable) {
                    Log.d(TAG, "onResponseForecastFail: ${t.message}")
                }
            }
        )
    }
}