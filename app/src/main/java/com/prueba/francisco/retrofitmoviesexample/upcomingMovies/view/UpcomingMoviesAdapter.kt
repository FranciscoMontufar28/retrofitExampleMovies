package com.prueba.francisco.retrofitmoviesexample.upcomingMovies.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.prueba.francisco.retrofitmoviesexample.R
import com.prueba.francisco.retrofitmoviesexample.upcomingMovies.data.model.Results

class UpcomingMoviesAdapter(): RecyclerView.Adapter<UpcomingMoviesAdapter.UpcomingMoviesViewHolder>() {

    var movies: List<Results> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingMoviesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.upcoming_movies_template,parent,false)
        return UpcomingMoviesViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return movies.count()?:0
    }

    override fun onBindViewHolder(holder: UpcomingMoviesViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    fun setData(listMovies: List<Results>){
        movies = listMovies
        notifyDataSetChanged()
    }

    class UpcomingMoviesViewHolder(view: View):RecyclerView.ViewHolder(view){

    }
}