package com.prueba.francisco.retrofitmoviesexample.popularMovies.data

import com.prueba.francisco.retrofitmoviesexample.popularMovies.data.model.Result
import com.prueba.francisco.retrofitmoviesexample.retrofit.RetrofitIntance
import com.prueba.francisco.retrofitmoviesexample.service.MovieDataService
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

object PopularMoviesAPI:PopularMoviesDataSource {

    override fun fetchPopularMovies(): Observable<List<Result>> {
        val retrofitService: MovieDataService = RetrofitIntance.getService()
        val popularMovies = retrofitService
            .getPopularMovies("e843cf7bc4e6d2a10ba5b19e7da99129")
            .flatMap {
                Single.fromCallable {
                    it.results
                }
            }
        return popularMovies.toObservable()
    }
}