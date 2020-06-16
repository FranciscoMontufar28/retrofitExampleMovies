package com.prueba.francisco.retrofitmoviesexample.upcomingMovies

import com.prueba.francisco.retrofitmoviesexample.upcomingMovies.data.model.UpcomingModel
import io.reactivex.rxjava3.core.Single

interface UpcomingMoviesContract {

    interface View{
        fun showLoader()
        fun hideLoader()
        fun showMovies(movies:Single<UpcomingModel>)
    }

    interface Presenter{
        fun getUpcomingMovies()
    }

    interface OnResponseCallback{
        fun onResponse(movies:Single<UpcomingModel>)
    }
}