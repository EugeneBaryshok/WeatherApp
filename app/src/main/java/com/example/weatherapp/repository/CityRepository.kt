package com.example.weatherapp.repository


import androidx.lifecycle.LiveData
import javax.inject.Inject
import javax.inject.Singleton
import androidx.lifecycle.switchMap
import com.example.weatherapp.AppExecutors
import com.example.weatherapp.api.ApiResponse
import com.example.weatherapp.api.ApiSuccessResponse
import com.example.weatherapp.api.WeatherResponse
import com.example.weatherapp.api.WeatherService
import com.example.weatherapp.db.WeatherDao
import com.example.weatherapp.db.WeatherDb
import com.example.weatherapp.util.AbsentLiveData
import com.example.weatherapp.vo.City
import com.example.weatherapp.vo.Resource
import com.example.weatherapp.vo.WeatherSearchResult


/**
 * Repository that handles User objects.
 */
//@OpenForTesting
@Singleton
class CityRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val weatherDao: WeatherDao,
    private val weatherService: WeatherService,
    private val db: WeatherDb
) {
    fun search(lat: Double, lon: Double): LiveData<Resource<List<City>>> {
        return object : NetworkBoundResource<List<City>, WeatherResponse>(appExecutors) {

            override fun saveCallResult(item: WeatherResponse) {
                val cityIds = item.data.map { it.id }
                val weatherSearchResult = WeatherSearchResult(
                    cityIds = cityIds,
                    lat = lat,
                    lon = lon,
                    totalCount = item.count,
                    query = "$lat,$lon"
                )
                db.runInTransaction {
                    weatherDao.insertCities(item.data)
                    weatherDao.insert(weatherSearchResult)
                }
            }

            override fun shouldFetch(data: List<City>?): Boolean {
//                return data == null || data.isEmpty()
                return true
            }

            override fun loadFromDb(): LiveData<List<City>> {
                return weatherDao.searchResult(lat, lon)
                    .switchMap { searchData ->
                        if (searchData == null) {
                            AbsentLiveData.create()
                        } else {
                            weatherDao.loadOrdered(searchData.cityIds)
                        }
                    }
            }

            override fun processResponse(response: ApiSuccessResponse<WeatherResponse>)
                    : WeatherResponse {
                return response.body
            }

            override fun createCall(): LiveData<ApiResponse<WeatherResponse>> {
                return weatherService.getCities(
                    lat,
                    lon,
                    appid = "a8cc6fd42a86a09559d3593973e1e7d3"
                )
            }
        }.asLiveData()
    }
    suspend fun delete(city: City) {
        weatherDao.deleteCity(city)
    }
}
