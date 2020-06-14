package com.prueba.francisco.retrofitmoviesexample.popularMovies.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.prueba.francisco.retrofitmoviesexample.App.Companion.CHANNEL_ID
import com.prueba.francisco.retrofitmoviesexample.R
import com.prueba.francisco.retrofitmoviesexample.popularMovies.PopularMoviesContract
import com.prueba.francisco.retrofitmoviesexample.popularMovies.data.model.Movie
import com.prueba.francisco.retrofitmoviesexample.popularMovies.data.model.Result
import com.prueba.francisco.retrofitmoviesexample.popularMovies.presenter.PopularMoviesPresenter
import com.prueba.francisco.retrofitmoviesexample.util.LifeCycleDisposable
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_popular_movies.*

class PopularMoviesFragment : Fragment(), PopularMoviesContract.View {

    private var popularMoviesPresenter: PopularMoviesContract.Presenter? = null

    private lateinit var movies: List<Result>

    val movieDisposable: LifeCycleDisposable = LifeCycleDisposable(this)
    var recyclerView: RecyclerView? = null
    var layoutManager: RecyclerView.LayoutManager? = null
    var swipeRefreshLayout: SwipeRefreshLayout? = null
    var notificationManager: NotificationManagerCompat? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        popularMoviesPresenter = PopularMoviesPresenter(this)
        popularMoviesPresenter?.getPopularMovies()
        setUpSwipeView()
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
        notificationManager =
            NotificationManagerCompat.from(requireContext())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.popular_movies_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.orientationMenuId -> {
                if (layoutManager is GridLayoutManager) {
                    layoutManager = LinearLayoutManager(requireContext())
                    item.title = "Grid View"
                    recyclerView?.layoutManager = layoutManager
                } else {
                    layoutManager = GridLayoutManager(requireContext(), 2)
                    item.title = "Vertical View"
                    recyclerView?.layoutManager = layoutManager
                }
            }
            else -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUpRecyclerView() {
        recyclerView = rvMoviesFragment
        layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.setHasFixedSize(true)
        recyclerView?.itemAnimator = DefaultItemAnimator()
        recyclerView?.adapter = movieAdapter
    }

    private val movieAdapter by lazy {
        PopularMoviesAdapter(
            object :
                ClickListener {
                override fun onClick(view: View, position: Int) {
                    val movieData = movies[position]
                    val titleMovie = movieData.title
                    val descriptionMovie = movieData.overview
                    val urlImg = movieData.poster_path
                    val rating = movieData.vote_average.toString()
                    Navigation.findNavController(view)
                        .navigate(
                            PopularMoviesFragmentDirections
                                .actionPopularMoviesFragment2ToMovieDetailsFragment(
                                    titleMovie,
                                    descriptionMovie,
                                    urlImg,
                                    rating
                                )
                        )
                }
            })
    }

    @SuppressLint("ResourceAsColor")
    private fun setUpSwipeView() {
        swipeRefreshLayout = swipe
        swipeRefreshLayout?.setColorSchemeColors(R.color.colorPrimary)
        swipeRefreshLayout?.setOnRefreshListener {
            popularMoviesPresenter?.getPopularMovies()
        }
    }

    override fun showLoader() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoader() {
        progressBar.visibility = View.GONE
    }

    override fun showPopularMovies(popularMovies: Single<Movie>) {
        popularMovies
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Movie> {
                override fun onSuccess(movieDBResponse: Movie?) {
                    if (movieDBResponse?.results != null) {
                        movies = movieDBResponse.results
                        movieAdapter.setData(movies)
                        swipeRefreshLayout?.isRefreshing = false
                    }
                }

                override fun onSubscribe(disposableMovie: Disposable?) {
                    if (disposableMovie != null) {
                        movieDisposable.add(disposableMovie)
                    }
                }

                override fun onError(e: Throwable?) {
                    showMessage("there is an error in the api request")
                }
            })
    }

    override fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    fun showNotification(title: String, description: String) {
        var notification = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(title)
            .setContentText(description)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .build()
        notificationManager?.notify(1, notification)
    }
}