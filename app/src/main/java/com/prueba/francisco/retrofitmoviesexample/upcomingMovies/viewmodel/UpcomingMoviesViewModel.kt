package com.prueba.francisco.retrofitmoviesexample.upcomingMovies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prueba.francisco.retrofitmoviesexample.upcomingMovies.data.UpcomingMoviesAPI
import com.prueba.francisco.retrofitmoviesexample.upcomingMovies.data.UpcomingMoviesLocal
import com.prueba.francisco.retrofitmoviesexample.upcomingMovies.data.UpcomingMoviesRepository
import com.prueba.francisco.retrofitmoviesexample.upcomingMovies.data.model.Results
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class UpcomingMoviesViewModel: ViewModel() {

    private val upcomingMoviesRepository = UpcomingMoviesRepository(UpcomingMoviesLocal, UpcomingMoviesAPI)

    var upcomingMoviesList : MutableLiveData<List<Results>> = MutableLiveData()

    fun getUpcomingMovies(): LiveData<List<Results>> {
        upcomingMoviesRepository.getUpcomingMoviesFromAPI2()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                upcomingMoviesList.value = it
            },{

            })
        return upcomingMoviesList
    }
}