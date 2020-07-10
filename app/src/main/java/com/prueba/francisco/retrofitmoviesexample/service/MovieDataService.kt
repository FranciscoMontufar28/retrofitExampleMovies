package com.prueba.francisco.retrofitmoviesexample.service

import com.prueba.francisco.retrofitmoviesexample.movieDetails.data.model.SimilarMovies
import com.prueba.francisco.retrofitmoviesexample.popularMovies.data.model.Movie
import com.prueba.francisco.retrofitmoviesexample.upcomingMovies.data.model.UpcomingModel
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDataService {

    @GET("movie/popular")
    fun getPopularMovies(@Query("api_key") apiKey: String): Single<Movie>

    @GET("movie/upcoming")
    fun getUpcomingMovies(@Query("api_key") apiKey: String): Single<UpcomingModel>

    @GET("movie/{id}/similar")
    fun getSimilarMovies(@Path("id") id: String, @Query("api_key") apiKey: String) : Single<SimilarMovies>
}