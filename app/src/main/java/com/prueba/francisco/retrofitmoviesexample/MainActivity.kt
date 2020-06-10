package com.prueba.francisco.retrofitmoviesexample

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.prueba.francisco.retrofitmoviesexample.adapter.ClickListener
import com.prueba.francisco.retrofitmoviesexample.adapter.MovieAdapter
import com.prueba.francisco.retrofitmoviesexample.model.Movie
import com.prueba.francisco.retrofitmoviesexample.model.Result
import com.prueba.francisco.retrofitmoviesexample.retrofit.RetrofitIntance
import com.prueba.francisco.retrofitmoviesexample.service.MovieDataService
import com.prueba.francisco.retrofitmoviesexample.util.LifeCycleDisposable
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val disposable : LifeCycleDisposable = LifeCycleDisposable(this)

    private lateinit var movies: ArrayList<Result>
    var recyclerView: RecyclerView? = null
    var movieAdapter: MovieAdapter? = null
    var layoutManager: RecyclerView.LayoutManager? = null
    var swipeRefreshLayout: SwipeRefreshLayout? = null

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "TMDB Popular Movies Today"
        getPopularMoviesRX()
        recyclerView = rvMovies
        //layoutManager = LinearLayoutManager(this)
        layoutManager = GridLayoutManager(this, 2)
        recyclerView?.layoutManager = layoutManager

        swipeRefreshLayout = swipe
        swipeRefreshLayout?.setColorSchemeColors(R.color.colorPrimary)
        swipeRefreshLayout?.setOnRefreshListener {
            getPopularMoviesRX()
        }
    }

    fun getPopularMoviesRX() {
        var movieDataService: MovieDataService = RetrofitIntance.getService()
        var moviesObservable = movieDataService.getPopularMoviesRX(this.getString(R.string.api_key))
        moviesObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Movie> {
                override fun onComplete() {
                    swipeRefreshLayout?.isRefreshing
                }

                override fun onNext(movieDBResponse: Movie?) {
                    if (movieDBResponse?.results != null) {
                        movies = movieDBResponse.results as ArrayList<Result>
                        showRecyclerView()
                    }
                }

                override fun onError(e: Throwable?) {
                    Toast.makeText(applicationContext, "there is an error in the api request", Toast.LENGTH_SHORT).show()
                }

                override fun onSubscribe(disposableMovie: Disposable?) {
                    if(disposableMovie != null){
                        disposable.add(disposableMovie)
                    }
                }
            })

    }

    fun showRecyclerView() {
        movieAdapter = MovieAdapter(this, movies, object : ClickListener {
            override fun onClick(view: View, position: Int) {
                Toast.makeText(applicationContext, movies[position].title, Toast.LENGTH_SHORT)
                    .show()
            }
        })
        recyclerView?.itemAnimator = DefaultItemAnimator()
        recyclerView?.adapter = movieAdapter
        swipeRefreshLayout?.isRefreshing = false
        movieAdapter!!.notifyDataSetChanged()

    }
}
