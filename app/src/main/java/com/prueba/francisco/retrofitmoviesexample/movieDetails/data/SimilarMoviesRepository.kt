package com.prueba.francisco.retrofitmoviesexample.movieDetails.data

import com.prueba.francisco.retrofitmoviesexample.movieDetails.data.model.SimilarMoviesResult
import io.reactivex.rxjava3.core.Observable

class SimilarMoviesRepository(private val similarMoviesAPI: SimilarMoviesAPI) {

    fun getSimilarMovies(id: String): Observable<List<SimilarMoviesResult>> {
        return similarMoviesAPI.fetchSimilarMovies(id)
    }
}