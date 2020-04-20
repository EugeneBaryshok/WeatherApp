package com.example.weatherapp.db


import android.util.SparseIntArray
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.room.*
import com.example.weatherapp.vo.City
import com.example.weatherapp.vo.WeatherSearchResult


/**
 * Interface for database access for User related operations.
 */
@Dao
abstract class WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(city: City)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(result: WeatherSearchResult)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertCities(repositories: List<City>)

    @Query("SELECT * FROM City")
    abstract fun getAllCities(): LiveData<List<City>>

    @Query("SELECT * FROM City WHERE `coord_lat` = :lat AND `coord_lon` = :lon")
    abstract fun search(lat: Double, lon: Double): LiveData<List<City>>

    @Query("SELECT * FROM WeatherSearchResult WHERE `lat` = :lat AND `lon` = :lon")
    abstract fun searchResult(lat: Double, lon: Double): LiveData<WeatherSearchResult?>

    fun loadOrdered(cityIds: List<Int>): LiveData<List<City>> {
        val order = SparseIntArray()
        cityIds.withIndex().forEach {
            order.put(it.value, it.index)
        }
        return loadById(cityIds).map { repositories ->
            repositories.sortedWith(compareBy { order.get(it.id) })
        }
    }
    @Query("SELECT * FROM City WHERE id in (:cityIds)")
    abstract fun loadById(cityIds: List<Int>): LiveData<List<City>>

    @Delete
    abstract suspend fun deleteCity(city: City)

}
