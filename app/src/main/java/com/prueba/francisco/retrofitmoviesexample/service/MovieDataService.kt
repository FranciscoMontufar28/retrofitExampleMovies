package com.prueba.francisco.retrofitmoviesexample.service

import com.prueba.francisco.retrofitmoviesexample.popularMovies.data.model.Movie
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDataService {

    @GET("movie/popular")
    fun getPopularMoviesRX(@Query("api_key") apiKey: String): Single<Movie>
}