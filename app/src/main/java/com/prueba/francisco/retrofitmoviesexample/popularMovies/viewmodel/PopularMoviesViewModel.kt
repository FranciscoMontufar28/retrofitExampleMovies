package com.prueba.francisco.retrofitmoviesexample.popularMovies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prueba.francisco.retrofitmoviesexample.popularMovies.data.PopularMoviesAPI
import com.prueba.francisco.retrofitmoviesexample.popularMovies.data.PopularMoviesLocal
import com.prueba.francisco.retrofitmoviesexample.popularMovies.data.PopularMoviesRepository
import com.prueba.francisco.retrofitmoviesexample.popularMovies.data.model.Result
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class PopularMoviesViewModel : ViewModel() {

    private val popularMoviesRepository = PopularMoviesRepository(PopularMoviesLocal,PopularMoviesAPI)

    fun getPopularMovies(): LiveData<List<Result>> {
        var movieResult : MutableLiveData<List<Result>> = MutableLiveData()
        popularMoviesRepository.getPopularMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                movieResult.value = it
            },{

            })
        return movieResult
    }

}