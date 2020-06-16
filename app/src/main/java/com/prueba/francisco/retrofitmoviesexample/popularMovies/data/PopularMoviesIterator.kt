package com.prueba.francisco.retrofitmoviesexample.popularMovies.data

import com.prueba.francisco.retrofitmoviesexample.popularMovies.data.model.Movie
import com.prueba.francisco.retrofitmoviesexample.popularMovies.PopularMoviesContract
import com.prueba.francisco.retrofitmoviesexample.retrofit.RetrofitIntance
import com.prueba.francisco.retrofitmoviesexample.service.MovieDataService
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class PopularMoviesIterator() {

    fun getPopularMoviesFromAPI(responseCallback: PopularMoviesContract.OnResponseCallback) {
        responseCallback.onResponse(getPopularMoviesRX())
    }

    private fun getPopularMoviesRX(): Single<Movie> {
        var movieDataService: MovieDataService = RetrofitIntance.getService()
        var moviesObservable =
            movieDataService.getPopularMovies("e843cf7bc4e6d2a10ba5b19e7da99129")
        moviesObservable
            .subscribeOn(Schedulers.io())
        return moviesObservable
    }
}