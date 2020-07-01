package com.prueba.francisco.retrofitmoviesexample

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.prueba.francisco.retrofitmoviesexample.popularMovies.data.PopularMoviesDAO
import com.prueba.francisco.retrofitmoviesexample.popularMovies.data.model.Result
import com.prueba.francisco.retrofitmoviesexample.upcomingMovies.data.UpcomingMoviesDAO
import com.prueba.francisco.retrofitmoviesexample.upcomingMovies.data.model.Results

@Database(entities = [Results::class, Result::class], version = 2)
abstract class ApplicationDataBase : RoomDatabase(){

    abstract fun getUpcomingDAO(): UpcomingMoviesDAO
    abstract fun getPopularDAO(): PopularMoviesDAO

    companion object{
        private var INSTANCE : ApplicationDataBase? = null

        fun getAppDataBase(context: Context): ApplicationDataBase?{
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    ApplicationDataBase::class.java,
                    "moviesApplicationPractice"
                ).allowMainThreadQueries()
                    .build()
            }
            return INSTANCE
        }
    }

    fun destroyInstance(){
        INSTANCE = null
    }
}