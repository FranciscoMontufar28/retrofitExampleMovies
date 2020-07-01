package com.prueba.francisco.retrofitmoviesexample.popularMovies.data

import com.prueba.francisco.retrofitmoviesexample.popularMovies.data.model.Result
import io.reactivex.rxjava3.core.Observable

interface PopularMoviesDataSource {

    fun fetchPopularMovies(): Observable<List<Result>>
}