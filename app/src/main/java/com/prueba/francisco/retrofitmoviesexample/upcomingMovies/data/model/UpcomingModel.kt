package com.prueba.francisco.retrofitmoviesexample.upcomingMovies.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "upcoming_movies")
data class Results(
    @PrimaryKey
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
    val results: List<Results> = ArrayList(),
    val total_results: String
)