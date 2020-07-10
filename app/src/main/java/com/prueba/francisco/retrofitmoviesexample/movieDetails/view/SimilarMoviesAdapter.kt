package com.prueba.francisco.retrofitmoviesexample.movieDetails.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.prueba.francisco.retrofitmoviesexample.R
import com.prueba.francisco.retrofitmoviesexample.movieDetails.data.model.SimilarMoviesResult
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.similar_movies_template.view.*

class SimilarMoviesAdapter : RecyclerView.Adapter<SimilarMoviesAdapter.ViewHolder>() {

    private var data: List<SimilarMoviesResult>? = null

    fun setData(list: List<SimilarMoviesResult>){
        this.data = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.similar_movies_template, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data?.count() ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvSimilar.text = data?.get(position)?.original_title
        var imagePatch = "https://image.tmdb.org/t/p/w500"+data?.
        get(position)?.poster_path
        Picasso.get().load(imagePatch).placeholder(R.drawable.loading).into(holder.imgSimilar)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imgSimilar: ImageView = view.img_similar
        var tvSimilar: TextView = view.tv_similar
    }
}