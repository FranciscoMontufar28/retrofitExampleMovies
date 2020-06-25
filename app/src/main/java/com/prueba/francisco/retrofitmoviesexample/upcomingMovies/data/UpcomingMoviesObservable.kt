package com.prueba.francisco.retrofitmoviesexample.upcomingMovies.data

import androidx.databinding.BaseObservable
import com.prueba.francisco.retrofitmoviesexample.upcomingMovies.data.model.UpcomingModel
import io.reactivex.rxjava3.core.Single

class UpcomingMoviesObservable: BaseObservable() {

    private val upcomingMoviesRepository = UpcomingMoviesRepository()

    fun getUpcomingMoviesFromAPI():Single<UpcomingModel>{
        return upcomingMoviesRepository.getUpcomingMoviesFromAPI()
    }
}