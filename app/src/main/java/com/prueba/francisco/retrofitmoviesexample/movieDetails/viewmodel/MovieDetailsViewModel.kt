package com.prueba.francisco.retrofitmoviesexample.movieDetails.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prueba.francisco.retrofitmoviesexample.movieDetails.data.SimilarMoviesAPI
import com.prueba.francisco.retrofitmoviesexample.movieDetails.data.SimilarMoviesRepository
import com.prueba.francisco.retrofitmoviesexample.movieDetails.data.model.SimilarMoviesResult
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class MovieDetailsViewModel : ViewModel() {

    private val similarMoviesRepository: SimilarMoviesRepository =
        SimilarMoviesRepository(SimilarMoviesAPI)

    fun getSimilarMovies(id: String): LiveData<List<SimilarMoviesResult>> {
        var similarMoviesResult: MutableLiveData<List<SimilarMoviesResult>> = MutableLiveData()
        similarMoviesRepository.getSimilarMovies(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                similarMoviesResult.value = it
            }, {

            })
        return similarMoviesResult
    }
}