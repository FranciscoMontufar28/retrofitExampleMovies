package com.prueba.francisco.retrofitmoviesexample

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.squareup.picasso.Picasso

class MovieDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var arg = arguments?.let { MovieDetailsFragmentArgs.fromBundle(it) }
        val title = view?.findViewById<TextView>(R.id.tvMovieTitleDetails)
        val description = view?.findViewById<TextView>(R.id.tvMovieDescriptionDetails)
        val image = view?.findViewById<ImageView>(R.id.ivMovieImgDetail)
        val ratingBar = view?.findViewById<RatingBar>(R.id.rbMovieDetail)
        val url = "https://image.tmdb.org/t/p/w500${arg?.urlMovieImg}"
        val rating = (arg?.ratingMovie?.toFloat())?.div(2)
        ratingBar?.rating = rating!!
        title?.text = arg?.titleMovie ?:"No title"
        description?.text = arg?.descriptionMovie?:"No Description"
        url.let {
            Picasso.get().load(it).placeholder(R.drawable.loading).into(image)
        }
    }
}