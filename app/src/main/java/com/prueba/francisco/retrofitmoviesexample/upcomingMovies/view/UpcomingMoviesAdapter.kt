package com.prueba.francisco.retrofitmoviesexample.upcomingMovies.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.prueba.francisco.retrofitmoviesexample.R
import com.prueba.francisco.retrofitmoviesexample.upcomingMovies.data.model.Movies
import com.prueba.francisco.retrofitmoviesexample.upcomingMovies.data.model.Results
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.upcoming_movies_template.view.*

class UpcomingMoviesAdapter(): RecyclerView.Adapter<UpcomingMoviesAdapter.UpcomingMoviesViewHolder>() {

    private var movies: List<Results> = listOf()

    fun setData(listMovies: List<Results>?){
        if (listMovies != null) {
            this.movies = listMovies
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingMoviesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.upcoming_movies_template, parent,false)
        return UpcomingMoviesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.count()
    }

    override fun onBindViewHolder(holder: UpcomingMoviesViewHolder, position: Int) {
        holder.title.text = movies[position].original_title
        holder.date.text = movies[position].release_date
        val url = "https://image.tmdb.org/t/p/w500${movies[position].poster_path}"
        Picasso.get().load(url).placeholder(R.drawable.loading).into(holder.image)
    }

    class UpcomingMoviesViewHolder(view: View):RecyclerView.ViewHolder(view){
        val title : TextView = view.tvTitleUpcoming
        val date : TextView = view.tvDateUpcoming
        val image : ImageView = view.ivImageUpcoming
    }
}