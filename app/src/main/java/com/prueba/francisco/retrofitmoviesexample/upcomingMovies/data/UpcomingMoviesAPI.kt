package com.prueba.francisco.retrofitmoviesexample.upcomingMovies.data

import com.prueba.francisco.retrofitmoviesexample.retrofit.RetrofitIntance
import com.prueba.francisco.retrofitmoviesexample.service.MovieDataService
import com.prueba.francisco.retrofitmoviesexample.upcomingMovies.data.model.Results
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

object UpcomingMoviesAPI : UpcomingMoviesDataSource {

    override fun fetchUpcomingMovies(): Observable<List<Results>> {
        val upcomingMoviesService: MovieDataService = RetrofitIntance.getService()
        val movieAPIData = upcomingMoviesService.getUpcomingMovies("e843cf7bc4e6d2a10ba5b19e7da99129")
            .flatMap {
                Single.fromCallable{
                    it.results
                }
            }
        return movieAPIData.toObservable()
    }

}