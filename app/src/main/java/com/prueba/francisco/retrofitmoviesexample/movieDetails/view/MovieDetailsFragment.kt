package com.prueba.francisco.retrofitmoviesexample.movieDetails.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prueba.francisco.retrofitmoviesexample.R
import com.prueba.francisco.retrofitmoviesexample.movieDetails.viewmodel.MovieDetailsViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_movie_details.*
import kotlinx.android.synthetic.main.fragment_popular_movies.*
import org.koin.android.ext.android.get

class MovieDetailsFragment : Fragment() {

    private var recyclerView: RecyclerView? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var movieDetailViewModel : MovieDetailsViewModel? = null
    private val similarMoviesAdapter by lazy {
        SimilarMoviesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        movieDetailViewModel = get()
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpSimilarRecycler()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var arg = arguments?.let {
            MovieDetailsFragmentArgs.fromBundle(
                it
            )
        }
        val title = view?.findViewById<TextView>(R.id.tvMovieTitleDetails)
        val description = view?.findViewById<TextView>(R.id.tvMovieDescriptionDetails)
        val image = view?.findViewById<ImageView>(R.id.ivMovieImgDetail)
        val ratingBar = view?.findViewById<RatingBar>(R.id.rbMovieDetail)
        val url = "https://image.tmdb.org/t/p/w500${arg?.urlMovieImg}"
        val rating = (arg?.ratingMovie?.toFloat())?.div(2)
        val id = arg?.id.toString()
        ratingBar?.rating = rating!!
        title?.text = arg?.titleMovie ?:"No title"
        description?.text = arg?.descriptionMovie?:"No Description"
        url.let {
            Picasso.get().load(it).placeholder(R.drawable.loading).into(image)
        }
        showSimilarMovies(id)
    }

    private fun setUpSimilarRecycler(){
        recyclerView = rvSimilarMovies
        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.setHasFixedSize(true)
        recyclerView?.itemAnimator = DefaultItemAnimator()
        recyclerView?.adapter = similarMoviesAdapter
    }

    private fun showSimilarMovies(id:String){
        movieDetailViewModel?.getSimilarMovies(id)?.observe(viewLifecycleOwner, Observer {
            similarMoviesAdapter.setData(it)
        })
    }
}