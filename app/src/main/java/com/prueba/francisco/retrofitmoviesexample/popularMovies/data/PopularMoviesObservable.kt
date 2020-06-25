package com.prueba.francisco.retrofitmoviesexample.popularMovies.data

import androidx.databinding.BaseObservable
import com.prueba.francisco.retrofitmoviesexample.popularMovies.data.model.Movie
import io.reactivex.rxjava3.core.Single

class PopularMoviesObservable: BaseObservable() {

    private val popularMoviesRepository = PopularMoviesRepository()

    fun getPopularMovies(): Single<Movie> {
       return popularMoviesRepository.getPopularMovies()
    }
}