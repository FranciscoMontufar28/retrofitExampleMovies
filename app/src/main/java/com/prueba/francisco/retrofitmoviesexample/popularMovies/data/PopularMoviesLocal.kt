package com.prueba.francisco.retrofitmoviesexample.popularMovies.data

import com.prueba.francisco.retrofitmoviesexample.App
import com.prueba.francisco.retrofitmoviesexample.ApplicationDataBase
import com.prueba.francisco.retrofitmoviesexample.popularMovies.data.model.Result
import io.reactivex.rxjava3.core.Observable

object PopularMoviesLocal : PopularMoviesDataSource {

    override fun fetchPopularMovies(): Observable<List<Result>> {
        return Observable.fromCallable{
           ApplicationDataBase.getAppDataBase(App.INSTANCE)
               ?.getPopularDAO()
               ?.getAllMovies()
        }
    }

    fun savePopularMovies(movies: List<Result>?) {
        if (movies != null) {
            ApplicationDataBase.getAppDataBase(App.INSTANCE)
                ?.getPopularDAO()
                ?.saveMovies(movies)
        }
    }
}