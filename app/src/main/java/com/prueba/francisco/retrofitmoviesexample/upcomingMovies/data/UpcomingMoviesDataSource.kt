package com.prueba.francisco.retrofitmoviesexample.upcomingMovies.data

import com.prueba.francisco.retrofitmoviesexample.upcomingMovies.data.model.Movies
import com.prueba.francisco.retrofitmoviesexample.upcomingMovies.data.model.Results
import io.reactivex.rxjava3.core.Observable

interface UpcomingMoviesDataSource {

    fun fetchUpcomingMovies(): Observable<List<Results>>
}