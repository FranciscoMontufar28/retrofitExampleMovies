package com.prueba.francisco.retrofitmoviesexample.upcomingMovies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prueba.francisco.retrofitmoviesexample.upcomingMovies.data.UpcomingMoviesObservable
import com.prueba.francisco.retrofitmoviesexample.upcomingMovies.data.model.Results
import com.prueba.francisco.retrofitmoviesexample.upcomingMovies.data.model.UpcomingModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable

class UpcomingMoviesViewModel: ViewModel() {

    private val upcomingMoviesObservable = UpcomingMoviesObservable()
    var upcomingMoviesList : MutableLiveData<List<Results>> = MutableLiveData()

    fun getUpcomingMovies(): LiveData<List<Results>> {
        upcomingMoviesObservable.getUpcomingMoviesFromAPI()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<UpcomingModel>{
                override fun onSuccess(upcomingMovies: UpcomingModel?) {
                    if (upcomingMovies != null){
                        upcomingMoviesList.value = upcomingMovies.results
                    }
                }

                override fun onSubscribe(d: Disposable?) {
                }

                override fun onError(e: Throwable?) {
                }

            })
        return upcomingMoviesList
    }
}