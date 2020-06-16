package com.prueba.francisco.retrofitmoviesexample.upcomingMovies.data.model

data class Results(
    val id: Int,
    val popularity: String,
    val vote_count: String,
    val video: Boolean,
    val poster_path: String,
    val adult: Boolean,
    val original_language: String,
    val original_title: String,
    val vote_average: String,
    val overview: String,
    val release_date: String
)

data class UpcomingModel(
    val results: List<Results>,
    val total_results: String
)