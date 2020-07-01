package com.prueba.francisco.retrofitmoviesexample.popularMovies.data

import com.prueba.francisco.retrofitmoviesexample.popularMovies.data.model.Result
import io.reactivex.rxjava3.core.Observable

class PopularMoviesRepository(
    val popularMoviesLocal: PopularMoviesLocal,
    val popularMoviesAPI: PopularMoviesAPI
) {

    fun getPopularMovies(): Observable<List<Result>> {
        return Observable.concat(
            popularMoviesLocal.fetchPopularMovies(),
            popularMoviesAPI.fetchPopularMovies()
        ).doOnNext(::savePopularMovies)
            .onErrorResumeNext { Observable.empty() }
    }

    private fun savePopularMovies(movies: List<Result>?) {
        popularMoviesLocal.savePopularMovies(movies)
    }
}