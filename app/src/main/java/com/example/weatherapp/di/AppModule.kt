package com.example.weatherapp.di

import android.app.Application
import androidx.room.Room
import com.example.weatherapp.api.APIClient
import com.example.weatherapp.api.WeatherService
import com.example.weatherapp.db.WeatherDao
import com.example.weatherapp.db.WeatherDb
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideWeatherService(): WeatherService =
        APIClient.client.create(WeatherService::class.java)


    @Singleton
    @Provides
    fun provideDb(app: Application): WeatherDb {
        return Room
            .databaseBuilder(app, WeatherDb::class.java, "weather.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideWeatherDao(db: WeatherDb): WeatherDao {
        return db.weatherDao()
    }

}
