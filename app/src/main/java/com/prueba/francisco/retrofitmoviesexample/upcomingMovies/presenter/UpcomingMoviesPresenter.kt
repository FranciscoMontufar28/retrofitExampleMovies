package com.prueba.francisco.retrofitmoviesexample.upcomingMovies.presenter

import com.prueba.francisco.retrofitmoviesexample.upcomingMovies.UpcomingMoviesContract
import com.prueba.francisco.retrofitmoviesexample.upcomingMovies.data.UpcomingMoviesIterator
import com.prueba.francisco.retrofitmoviesexample.upcomingMovies.data.model.UpcomingModel
import io.reactivex.rxjava3.core.Single

class UpcomingMoviesPresenter(private val view: UpcomingMoviesContract.View): UpcomingMoviesContract.Presenter {

    private val upcomingMoviesIterator = UpcomingMoviesIterator()

    override fun getUpcomingMovies() {
        view.showLoader()
        upcomingMoviesIterator.getUpcomingMoviesFromAPI(object : UpcomingMoviesContract.OnResponseCallback{
            override fun onResponse(movies: Single<UpcomingModel>) {
                view.hideLoader()
                view.showMovies(movies)
            }
        })
    }
}