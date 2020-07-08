package com.prueba.francisco.retrofitmoviesexample.popularMovies.viewmodel

import android.widget.SearchView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prueba.francisco.retrofitmoviesexample.popularMovies.data.PopularMoviesAPI
import com.prueba.francisco.retrofitmoviesexample.popularMovies.data.PopularMoviesLocal
import com.prueba.francisco.retrofitmoviesexample.popularMovies.data.PopularMoviesRepository
import com.prueba.francisco.retrofitmoviesexample.popularMovies.data.model.Result
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class PopularMoviesViewModel : ViewModel() {

    private val popularMoviesRepository =
        PopularMoviesRepository(PopularMoviesLocal, PopularMoviesAPI)
    private var movieResult: MutableLiveData<List<Result>> = MutableLiveData()
    private var movieResultByName: MutableLiveData<List<Result>> = MutableLiveData()
    var subject: PublishSubject<String> = PublishSubject.create()

    fun getPopularMovies(): LiveData<List<Result>> {
        popularMoviesRepository.getPopularMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                movieResult.value = it
            }, {

            })
        return movieResult
    }

    private fun onSearchMovie(searchView: SearchView): PublishSubject<String> {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                subject.onComplete()
                searchView.clearFocus()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                subject.onNext(newText)
                return false
            }
        })
        return subject
    }

    fun onCloseSearchMovie(searchView: SearchView): LiveData<List<Result>> {
        searchView.setOnCloseListener {
            movieResultByName.value = movieResult.value
            false
        }
        return movieResultByName
    }

    fun onSearchMovieResult(searchView: SearchView): LiveData<List<Result>> {
        onSearchMovie(searchView)
            ?.debounce(300, TimeUnit.MILLISECONDS)
            ?.filter { text -> text.isNotEmpty() && text.length >= 3 }
            ?.map { text -> text.toLowerCase().trim() }
            ?.distinctUntilChanged()
            ?.switchMap { query -> PublishSubject.just(query) }
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                val movies = popularMoviesRepository.getPopularMoviesByName(it)
                if (movies != null) {
                    movieResultByName.value = movies
                }
            }, {

            })
        return movieResultByName
    }

    override fun onCleared() {
        super.onCleared()
        movieResult = MutableLiveData()
    }

}