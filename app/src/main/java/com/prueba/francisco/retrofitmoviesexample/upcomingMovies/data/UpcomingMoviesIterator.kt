package com.prueba.francisco.retrofitmoviesexample.upcomingMovies.data

import com.prueba.francisco.retrofitmoviesexample.retrofit.RetrofitIntance
import com.prueba.francisco.retrofitmoviesexample.service.MovieDataService
import com.prueba.francisco.retrofitmoviesexample.upcomingMovies.UpcomingMoviesContract
import com.prueba.francisco.retrofitmoviesexample.upcomingMovies.data.model.UpcomingModel
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class UpcomingMoviesIterator() {

    fun getUpcomingMoviesFromAPI(responseCallback: UpcomingMoviesContract.OnResponseCallback){
        responseCallback.onResponse(getUpcomingMovies())
    }

    private fun getUpcomingMovies():Single<UpcomingModel>{
        val upcomingMoviesService : MovieDataService = RetrofitIntance.getService()
        return upcomingMoviesService.getUpcomingMovies("e843cf7bc4e6d2a10ba5b19e7da99129")
            .subscribeOn(Schedulers.io())
    }
}