package com.prueba.francisco.retrofitmoviesexample.upcomingMovies.data.model

import androidx.room.Entity
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey

@Entity(tableName = "upcoming_movies")
data class Movies(
    @PrimaryKey
    val id: Int,
    val original_title: String,
    val release_date: String,
    val poster_path: String
)