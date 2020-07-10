package com.prueba.francisco.retrofitmoviesexample.upcomingMovies.data

import com.prueba.francisco.retrofitmoviesexample.upcomingMovies.data.model.Results
import io.reactivex.rxjava3.core.Observable

class UpcomingMoviesRepository(
    private val upcomingmoviesLocal: UpcomingMoviesLocal,
    private val upcomingMoviesAPI: UpcomingMoviesAPI
) {

    fun getUpcomingMoviesFromAPI2(): Observable<List<Results>> {
        return Observable.concat(
            upcomingmoviesLocal.fetchUpcomingMovies()
            , upcomingMoviesAPI.fetchUpcomingMovies()
        ).distinct()
            .doOnNext(::saveUpcomingMovies)
            .onErrorResumeNext { Observable.empty() }
    }

    private fun saveUpcomingMovies(movies: List<Results>?) {
        upcomingmoviesLocal.saveUpcomingMovies(movies)
    }
}