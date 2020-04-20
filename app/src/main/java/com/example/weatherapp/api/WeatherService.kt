package com.example.weatherapp.api

import androidx.lifecycle.LiveData
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("find")
    fun getCities(@Query("lat") lat: Double,  @Query("lon") lon: Double,  @Query("units") units: String="metric", @Query("lang") lang: String="ru", @Query("appid") appid: String) : LiveData<ApiResponse<WeatherResponse>>
}