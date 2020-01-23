package com.prueba.francisco.retrofitmoviesexample.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.prueba.francisco.retrofitmoviesexample.R
import com.prueba.francisco.retrofitmoviesexample.model.Result
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_list_item_template.view.*

class MovieAdapter(var context: Context, movieArrayList: ArrayList<Result>, var clickListener: ClickListener) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private var movies: ArrayList<Result>? = null

    init {
        this.movies = movieArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.movie_list_item_template,parent,false)
        return ViewHolder(view,clickListener)
    }

    override fun getItemCount(): Int {
        return movies?.count()?:0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.movieTitle.text = movies?.get(position)?.original_title
        holder.movieRating.text = movies?.get(position)?.vote_average.toString()
        var imagePatch = "https://image.tmdb.org/t/p/w500"+movies?.
            get(position)?.poster_path
        Picasso.get().load(imagePatch).placeholder(R.drawable.loading).into(holder.movieImage)
    }

    class ViewHolder(itemView:View, clickListener: ClickListener):RecyclerView.ViewHolder(itemView),View.OnClickListener{
        var movieTitle = itemView.tvTitle
        var movieRating = itemView.tvRating
        var movieImage = itemView.ivMovie
        var listener:ClickListener? = null

        init {
            itemView.setOnClickListener(this)
            this.listener = clickListener
        }

        override fun onClick(v: View?) {
            this.listener?.onClick(v!!,adapterPosition)
        }

    }
}