package com.prueba.francisco.retrofitmoviesexample.popularMovies

import com.prueba.francisco.retrofitmoviesexample.popularMovies.data.model.Movie
import io.reactivex.rxjava3.core.Single

interface PopularMoviesContract {

    interface View {
        fun showLoader()
        fun hideLoader()
        fun showPopularMovies(popularMovies: Single<Movie>)
        fun showMessage(message: String)
    }

    interface Presenter {
        fun getPopularMovies()
    }

    interface OnResponseCallback {
        fun onResponse(popularMoviesSingle: Single<Movie>)
    }
}