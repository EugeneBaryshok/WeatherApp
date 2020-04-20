package com.example.weatherapp.ui.common

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import com.example.weatherapp.AppExecutors
import com.example.weatherapp.R
import com.example.weatherapp.databinding.CityItemBinding
import com.example.weatherapp.vo.City

/**
 * A RecyclerView adapter for [City] class.
 */
class CityListAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val showFullName: Boolean
) : DataBoundListAdapter<City, CityItemBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<City>() {
        override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem.name == newItem.name
        }
    }
) {

    override fun createBinding(parent: ViewGroup): CityItemBinding {
        val binding = DataBindingUtil.inflate<CityItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.city_item,
            parent,
            false,
            dataBindingComponent
        )
        binding.showFullName = showFullName
        return binding
    }


    override fun bind(binding: CityItemBinding, item: City) {
        binding.city = item
    }
}
