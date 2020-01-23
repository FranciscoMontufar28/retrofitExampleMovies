package com.prueba.francisco.retrofitmoviesexample

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Toast
import com.prueba.francisco.retrofitmoviesexample.adapter.ClickListener
import com.prueba.francisco.retrofitmoviesexample.adapter.MovieAdapter
import com.prueba.francisco.retrofitmoviesexample.model.Movie
import com.prueba.francisco.retrofitmoviesexample.model.Result
import com.prueba.francisco.retrofitmoviesexample.retrofit.RetrofitIntance
import com.prueba.francisco.retrofitmoviesexample.service.MovieDataService
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var movies: ArrayList<Result>
    var recyclerView:RecyclerView? = null
    var movieAdapter: MovieAdapter? = null
    var layoutManager:RecyclerView.LayoutManager? = null
    var swipeRefreshLayout : SwipeRefreshLayout? = null

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "TMDB Popular Movies Today"
        getPopularMovies()
        recyclerView = rvMovies
        //layoutManager = LinearLayoutManager(this)
        layoutManager = GridLayoutManager(this, 2)
        recyclerView?.layoutManager = layoutManager

        swipeRefreshLayout = swipe
        swipeRefreshLayout?.setColorSchemeColors(R.color.colorPrimary)
        swipeRefreshLayout?.setOnRefreshListener {
            getPopularMovies()
        }
    }

    fun getPopularMovies() {
        var movieDataService: MovieDataService = RetrofitIntance.getService()
        var call = movieDataService.getPopularMovies(this.getString(R.string.api_key))
        call.enqueue(object : Callback<Movie> {
            override fun onFailure(call: Call<Movie>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                Log.d("Retrofit", response.message())
                var movieDBResponse = response.body()
                if (movieDBResponse?.results != null) {
                    movies = movieDBResponse.results as ArrayList<Result>
                    showRecyclerView()
                }
            }
        })
    }

    fun showRecyclerView(){
        movieAdapter = MovieAdapter(this,movies,object : ClickListener{
            override fun onClick(view: View, position: Int) {
                Toast.makeText(applicationContext, movies[position].title, Toast.LENGTH_SHORT).show()
            }
        })
        recyclerView?.itemAnimator = DefaultItemAnimator()
        recyclerView?.adapter = movieAdapter
        swipeRefreshLayout?.isRefreshing = false
        movieAdapter!!.notifyDataSetChanged()

    }
}
