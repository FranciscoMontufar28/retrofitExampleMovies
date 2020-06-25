package com.prueba.francisco.retrofitmoviesexample.popularMovies.data

import androidx.lifecycle.MutableLiveData
import com.prueba.francisco.retrofitmoviesexample.popularMovies.data.model.Movie
import com.prueba.francisco.retrofitmoviesexample.popularMovies.data.model.Result
import com.prueba.francisco.retrofitmoviesexample.retrofit.RetrofitIntance
import com.prueba.francisco.retrofitmoviesexample.service.MovieDataService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class PopularMoviesRepository() {

    var message = MutableLiveData<String>()

    fun getErrorMessage(): MutableLiveData<String> {
        return message
    }

    fun getPopularMovies(): Single<Movie> {
        var movieDataService: MovieDataService = RetrofitIntance.getService()
        var moviesObservable =
            movieDataService.getPopularMovies("e843cf7bc4e6d2a10ba5b19e7da99129")
        moviesObservable
            .subscribeOn(Schedulers.io())
        return moviesObservable
    }
}