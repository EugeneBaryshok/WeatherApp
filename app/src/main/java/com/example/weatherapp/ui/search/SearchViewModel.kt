package com.example.weatherapp.ui.search

import androidx.lifecycle.*
import com.example.weatherapp.repository.CityRepository
import com.example.weatherapp.util.AbsentLiveData
import com.example.weatherapp.vo.City
import com.example.weatherapp.vo.Resource
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val cityRepository: CityRepository) :
    ViewModel() {

    private val _query = MutableLiveData<String>()

    val query: LiveData<String> = _query

    val results: LiveData<Resource<List<City>>> = _query.switchMap { search ->
        if (search.isBlank()) {
            AbsentLiveData.create()
        } else {
            val sepPose: Int = search.indexOf(",") //this finds the first occurrence of "."
            var latString = "0.0"
            var lonString = "0.0"
            if (sepPose != -1) {
                latString = search.substring(0, sepPose) //this will give abc
                lonString = search.substring(sepPose + 1, search.length)
            }
            cityRepository.search(latString.toDouble(), lonString.toDouble())
        }
    }


    fun setQuery(originalInput: String) {
        val input = originalInput.toLowerCase(Locale.getDefault()).trim()
        if (input == _query.value) {
            return
        }
        _query.value = input
    }

//    fun refresh() {
//        _query.value?.let {
//            _query.value = it
//        }
//    }

    fun delete(city: City) {
        viewModelScope.launch {
            cityRepository.delete(city)
        }
    }

}
