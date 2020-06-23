package com.prueba.francisco.retrofitmoviesexample.popularMovies.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prueba.francisco.retrofitmoviesexample.popularMovies.data.PopularMoviesObservable
import com.prueba.francisco.retrofitmoviesexample.popularMovies.data.model.Movie
import com.prueba.francisco.retrofitmoviesexample.popularMovies.data.model.Result
import com.prueba.francisco.retrofitmoviesexample.popularMovies.view.ClickListener
import com.prueba.francisco.retrofitmoviesexample.popularMovies.view.PopularMoviesAdapter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable

class PopularMoviesViewModel : ViewModel() {

    private var adapter: PopularMoviesAdapter? = null
    private val popularMoviesObservable = PopularMoviesObservable()



    fun getPopularMovies(): MutableLiveData<List<Result>> {
        var movieResult : MutableLiveData<List<Result>> = MutableLiveData()
        popularMoviesObservable.getPopularMovies()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Movie>{
                override fun onSuccess(movies: Movie?) {
                    if (movies !=null){
                        movieResult.value = movies.results
                    }
                }

                override fun onSubscribe(d: Disposable?) {
                }

                override fun onError(e: Throwable?) {
                }

            })
        return movieResult
    }

}