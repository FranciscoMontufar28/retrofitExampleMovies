package com.prueba.francisco.retrofitmoviesexample.upcomingMovies.data

import com.prueba.francisco.retrofitmoviesexample.App
import com.prueba.francisco.retrofitmoviesexample.ApplicationDataBase
import com.prueba.francisco.retrofitmoviesexample.upcomingMovies.data.model.Results
import io.reactivex.rxjava3.core.Observable

object UpcomingMoviesLocal : UpcomingMoviesDataSource {

    override fun fetchUpcomingMovies(): Observable<List<Results>> {
        return Observable.fromCallable {
            ApplicationDataBase.getAppDataBase(App.INSTANCE)
                ?.getUpcomingDAO()
                ?.getAllMovies()
        }
    }

    fun saveUpcomingMovies(movies: List<Results>?) {
        if (movies != null) {
            ApplicationDataBase.getAppDataBase(App.INSTANCE)
                ?.getUpcomingDAO()
                ?.saveUpcomingMovies(movies)
        }
    }
}
