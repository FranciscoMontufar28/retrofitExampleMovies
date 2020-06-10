package com.prueba.francisco.retrofitmoviesexample.service

import com.prueba.francisco.retrofitmoviesexample.model.Movie
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDataService {

    @GET("movie/popular")
    fun getPopularMoviesRX(@Query("api_key") apiKey: String): Observable<Movie>
}