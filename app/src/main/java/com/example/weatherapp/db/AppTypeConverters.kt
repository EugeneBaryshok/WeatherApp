package com.example.weatherapp.db


import androidx.room.TypeConverter
import com.example.weatherapp.vo.Weather
import com.google.gson.reflect.TypeToken
import timber.log.Timber
import java.util.*
import com.google.gson.Gson
import kotlin.collections.ArrayList


object AppTypeConverters {
    private var gson = Gson()

    @TypeConverter
    @JvmStatic
    fun toDate(dateLong: Long?): Date? {
        return if (dateLong == null) null else Date(dateLong)
    }

    @TypeConverter
    @JvmStatic
    fun fromDate(date: Date?): Long? {
        return (date?.time)
    }

    @JvmStatic
    @TypeConverter
    fun stringToWeatherList(data: String?): List<Weather> {
        if (data == null) {
            return ArrayList()
        }

        val listType = object : TypeToken<List<Weather>>() {
        }.type

        return gson.fromJson(data, listType)
    }


    @JvmStatic
    @TypeConverter
    fun someWeatherListToString(someObjects: List<Weather>): String {
        return gson.toJson(someObjects)
    }

    @TypeConverter
    @JvmStatic
    fun stringToIntList(data: String?): List<Int>? {
        return data?.let {
            it.split(",").map {
                try {
                    it.toInt()
                } catch (ex: NumberFormatException) {
                    Timber.e(ex, "Cannot convert $it to number")
                    null
                }
            }
        }?.filterNotNull()
    }

    @TypeConverter
    @JvmStatic
    fun intListToString(ints: List<Int>?): String? {
        return ints?.joinToString(",")
    }
}
