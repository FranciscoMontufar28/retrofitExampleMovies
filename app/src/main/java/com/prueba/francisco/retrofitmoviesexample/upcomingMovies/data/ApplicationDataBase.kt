package com.prueba.francisco.retrofitmoviesexample.upcomingMovies.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.prueba.francisco.retrofitmoviesexample.upcomingMovies.data.model.Results

@Database(entities = [Results::class], version = 2)
abstract class ApplicationDataBase : RoomDatabase(){

    abstract fun getDAO() : UpcomingMoviesDAO

    companion object{
        private var INSTANCE : ApplicationDataBase? = null

        fun getAppDataBase(context: Context):ApplicationDataBase?{
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