package com.example.weatherapp.vo

import androidx.room.*
import com.example.weatherapp.api.WeatherResponse
import com.example.weatherapp.db.AppTypeConverters
import com.google.gson.annotations.SerializedName

@TypeConverters(AppTypeConverters::class)
@Entity(
    indices = [
        Index("id")],
    primaryKeys = ["id"]
)
data class Weather(
    var id: Int,
    var main: String,
    var icon: String
) {}