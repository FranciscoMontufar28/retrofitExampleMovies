package com.prueba.francisco.retrofitmoviesexample.movieDetails.data

import com.prueba.francisco.retrofitmoviesexample.movieDetails.data.model.SimilarMoviesResult
import com.prueba.francisco.retrofitmoviesexample.retrofit.RetrofitInstance
import com.prueba.francisco.retrofitmoviesexample.service.MovieDataService
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

object SimilarMoviesAPI {

    fun fetchSimilarMovies(id:String): Observable<List<SimilarMoviesResult>> {
        val retrofitService: MovieDataService = RetrofitInstance.getService()
        val similarMovies = retrofitService
            .getSimilarMovies(id,"e843cf7bc4e6d2a10ba5b19e7da99129")
            .flatMap {
                Single.fromCallable {
                    it.results
                }
            }
        return similarMovies.toObservable()
    }
}