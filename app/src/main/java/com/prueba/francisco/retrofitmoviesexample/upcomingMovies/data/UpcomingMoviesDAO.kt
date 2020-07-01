package com.prueba.francisco.retrofitmoviesexample.upcomingMovies.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.prueba.francisco.retrofitmoviesexample.upcomingMovies.data.model.Results

@Dao
interface UpcomingMoviesDAO {

    @Query("SELECT * FROM upcoming_movies")
    fun getAllMovies(): List<Results>

    @Query("SELECT * FROM upcoming_movies WHERE original_title LIKE :title")
    fun findUpcomingMovieByName(title:String): List<Results>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveUpcomingMovies(movies: List<Results>)
}