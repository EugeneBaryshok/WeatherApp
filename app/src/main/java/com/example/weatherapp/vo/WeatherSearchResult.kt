package com.example.weatherapp.vo

import androidx.room.Entity
import androidx.room.TypeConverters
import com.example.weatherapp.db.AppTypeConverters

@Entity(primaryKeys = ["query"])
@TypeConverters(AppTypeConverters::class)
data class WeatherSearchResult(
    val query: String,
    val cityIds: List<Int>,
    val totalCount: Int,
    val lat: Double,
    val lon: Double
)
