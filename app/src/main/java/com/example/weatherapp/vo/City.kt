package com.example.weatherapp.vo

import androidx.room.*
import com.example.weatherapp.api.WeatherResponse
import com.example.weatherapp.db.AppTypeConverters
import com.google.gson.annotations.SerializedName

@TypeConverters(AppTypeConverters::class)
@Entity(
    indices = [
        Index("id")],
    primaryKeys = ["name"]
)
data class City(
    var id: Int,
    var name: String,
    @field:SerializedName("coord")
    @field:Embedded(prefix = "coord_")
    var coord: Coord,
    @field:SerializedName("main")
    @field:Embedded(prefix = "main_")
    var mainInfo: MainInfo,
    var weather: List<Weather>
) {
    data class Coord(
        var lon: Double,
        var lat: Double
    )

    data class CityWeatherResponse(
        var data: List<Weather>
    )

    data class MainInfo(
        var temp: Double,
        var pressure: Int,
        var humidity: Int
    )
    {
        fun tempString():String
        {
            return "$temp°"
        }
    }

//    data class Weather(
//        var id: Int,
//        var main: String,
//        var icon: String,
//        var description: String
//    )

}