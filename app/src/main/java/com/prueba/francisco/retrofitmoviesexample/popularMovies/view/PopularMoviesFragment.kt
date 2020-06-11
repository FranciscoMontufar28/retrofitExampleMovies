package com.prueba.francisco.retrofitmoviesexample.popularMovies.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.prueba.francisco.retrofitmoviesexample.R
import com.prueba.francisco.retrofitmoviesexample.adapter.ClickListener
import com.prueba.francisco.retrofitmoviesexample.adapter.MovieAdapter
import com.prueba.francisco.retrofitmoviesexample.model.Movie
import com.prueba.francisco.retrofitmoviesexample.model.Result
import com.prueba.francisco.retrofitmoviesexample.retrofit.RetrofitIntance
import com.prueba.francisco.retrofitmoviesexample.service.MovieDataService
import com.prueba.francisco.retrofitmoviesexample.util.LifeCycleDisposable
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_popular_movies.*

class PopularMoviesFragment : androidx.fragment.app.Fragment() {

    private lateinit var movies: ArrayList<Result>
    val disposable : LifeCycleDisposable = LifeCycleDisposable(this)
    var recyclerView : androidx.recyclerview.widget.RecyclerView? = null
    var layoutManager : androidx.recyclerview.widget.RecyclerView.LayoutManager? = null
    var swipeRefreshLayout: androidx.swiperefreshlayout.widget.SwipeRefreshLayout? = null

    private val movieAdapter by lazy {
        MovieAdapter(movies, object : ClickListener {
            override fun onClick(view: View, position: Int) {
                Toast.makeText(requireContext(), movies[position].title, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = rvMoviesFragment
        layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.setHasFixedSize(true)
        getPopularMoviesRX()
        swipeRefreshLayout = swipe
        swipeRefreshLayout?.setColorSchemeColors(R.color.colorPrimary)
        swipeRefreshLayout?.setOnRefreshListener {
            getPopularMoviesRX()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_popular_movies, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar?.title = "Popular Movies"
    }

    private fun getPopularMoviesRX() {
        var movieDataService: MovieDataService = RetrofitIntance.getService()
        var moviesObservable = movieDataService.getPopularMoviesRX(this.getString(R.string.api_key))
        moviesObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Movie>{
                override fun onSuccess(movieDBResponse: Movie?) {
                    if (movieDBResponse?.results != null) {
                        movies = movieDBResponse.results as ArrayList<Result>
                        showRecyclerView()
                    }
                }

                override fun onSubscribe(disposableMovie: Disposable?) {
                    if(disposableMovie != null){
                        disposable.add(disposableMovie)
                    }
                }

                override fun onError(e: Throwable?) {
                    Toast.makeText(requireContext(), "there is an error in the api request", Toast.LENGTH_SHORT).show()
                }
            })
    }

    fun showRecyclerView() {
        recyclerView?.itemAnimator = DefaultItemAnimator()
        recyclerView?.adapter = movieAdapter
        swipeRefreshLayout?.isRefreshing = false
        movieAdapter!!.notifyDataSetChanged()
    }
}