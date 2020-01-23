package com.prueba.francisco.retrofitmoviesexample.service

import com.prueba.francisco.retrofitmoviesexample.model.Movie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDataService {

    @GET("movie/popular")
    fun getPopularMovies(@Query("api_key") apiKey: String): Call<Movie>
}