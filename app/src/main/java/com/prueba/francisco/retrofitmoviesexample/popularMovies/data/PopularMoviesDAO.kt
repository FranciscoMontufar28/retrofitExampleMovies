package com.prueba.francisco.retrofitmoviesexample.popularMovies.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.prueba.francisco.retrofitmoviesexample.popularMovies.data.model.Result

@Dao
interface PopularMoviesDAO {

    @Query("SELECT * FROM popular_movies")
    fun getAllMovies():List<Result>

    @Query("SELECT * FROM popular_movies WHERE original_title LIKE '%' || :name || '%'" )
    fun getMoviesByName(name:String):List<Result>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveMovies(movies:List<Result>)
}