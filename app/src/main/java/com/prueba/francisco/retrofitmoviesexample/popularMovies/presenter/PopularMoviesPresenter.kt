package com.prueba.francisco.retrofitmoviesexample.popularMovies.presenter

import com.prueba.francisco.retrofitmoviesexample.popularMovies.data.model.Movie
import com.prueba.francisco.retrofitmoviesexample.popularMovies.PopularMoviesContract
import com.prueba.francisco.retrofitmoviesexample.popularMovies.data.PopularMoviesIterator
import io.reactivex.rxjava3.core.Single

class PopularMoviesPresenter(private val view: PopularMoviesContract.View) : PopularMoviesContract.Presenter {

    private val popularMoviesIterator = PopularMoviesIterator()

    override fun getPopularMovies(){
        view.showLoader()
        popularMoviesIterator.getPopularMoviesFromAPI(object : PopularMoviesContract.OnResponseCallback{
            override fun onResponse(popularMoviesSingle: Single<Movie>) {
                view.hideLoader()
                view.showPopularMovies(popularMoviesSingle)
            }
        })
    }
}