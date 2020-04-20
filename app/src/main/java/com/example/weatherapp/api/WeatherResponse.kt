package com.example.weatherapp.api

import androidx.room.TypeConverters
import com.example.weatherapp.db.AppTypeConverters
import com.example.weatherapp.vo.City
import com.google.gson.annotations.SerializedName

@TypeConverters(AppTypeConverters::class)
data class WeatherResponse(
    @SerializedName("message")
    val message: String = "",
    @SerializedName("count")
    val count: Int = 0,
    @SerializedName("list")
    val data: List<City>
) {}
