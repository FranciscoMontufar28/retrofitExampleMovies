package com.prueba.francisco.retrofitmoviesexample.popularMovies.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.prueba.francisco.retrofitmoviesexample.R
import com.prueba.francisco.retrofitmoviesexample.popularMovies.data.model.Result
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_list_item_template.view.*

class PopularMoviesAdapter( var clickListener: ClickListener) : RecyclerView.Adapter<PopularMoviesAdapter.ViewHolder>() {

    private var movieList: List<Result>? = null

    fun setData(movies: List<Result>){
        this.movieList = movies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.movie_list_item_template,parent,false)
        return ViewHolder(
            view,
            clickListener
        )
    }

    override fun getItemCount(): Int {
        return movieList?.count()?:0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.movieTitle.text = movieList?.get(position)?.original_title
        holder.movieRating.text = movieList?.get(position)?.vote_average.toString()
        var imagePatch = "https://image.tmdb.org/t/p/w500"+movieList?.
        get(position)?.poster_path
        Picasso.get().load(imagePatch).placeholder(R.drawable.loading).into(holder.movieImage)
    }

    class ViewHolder(itemView:View, clickListener: ClickListener):RecyclerView.ViewHolder(itemView),View.OnClickListener{
        var movieTitle: TextView = itemView.tvTitle
        var movieRating: TextView = itemView.tvRating
        var movieImage: ImageView = itemView.ivMovie
        private var listener: ClickListener? = null

        init {
            itemView.setOnClickListener(this)
            this.listener = clickListener
        }

        override fun onClick(v: View?) {
            this.listener?.onClick(v!!,adapterPosition)
        }

    }
}