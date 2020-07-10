package com.prueba.francisco.retrofitmoviesexample.upcomingMovies.data

import androidx.room.*
import com.prueba.francisco.retrofitmoviesexample.upcomingMovies.data.model.Results
import com.prueba.francisco.retrofitmoviesexample.upcomingMovies.data.model.UpcomingModel

@Dao
interface UpcomingMoviesDAO {

    @Query("SELECT * FROM upcoming_movies")
    fun getAllMovies(): List<Results>

    @Query("SELECT * FROM upcoming_movies WHERE original_title LIKE :title")
    fun findUpcomingMovieByName(title:String): List<Results>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveUpcomingMovies(movies: List<Results>)

    @Query("DELETE FROM upcoming_movies")
    fun cleanUpcomingMovies()
}