package com.example.weatherapp.db


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherapp.vo.City
import com.example.weatherapp.vo.Weather
import com.example.weatherapp.vo.WeatherSearchResult

/**
 * Main database description.
 */
@Database(
    entities = [City::class, Weather::class, WeatherSearchResult::class],
    version = 7,
    exportSchema = false
)
abstract class WeatherDb : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}
