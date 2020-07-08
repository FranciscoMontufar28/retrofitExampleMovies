package com.prueba.francisco.retrofitmoviesexample.koin

import com.prueba.francisco.retrofitmoviesexample.popularMovies.viewmodel.PopularMoviesViewModel
import com.prueba.francisco.retrofitmoviesexample.upcomingMovies.viewmodel.UpcomingMoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelKoinModule = module {
    viewModel { PopularMoviesViewModel() }
    viewModel { UpcomingMoviesViewModel() }
}
