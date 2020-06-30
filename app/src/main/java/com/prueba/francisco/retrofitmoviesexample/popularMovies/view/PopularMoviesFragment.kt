package com.prueba.francisco.retrofitmoviesexample.popularMovies.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.prueba.francisco.retrofitmoviesexample.R
import com.prueba.francisco.retrofitmoviesexample.databinding.FragmentPopularMoviesBinding
import com.prueba.francisco.retrofitmoviesexample.popularMovies.data.model.Result
import com.prueba.francisco.retrofitmoviesexample.popularMovies.viewmodel.PopularMoviesViewModel
import com.prueba.francisco.retrofitmoviesexample.util.LifeCycleDisposable
import kotlinx.android.synthetic.main.fragment_popular_movies.*

class PopularMoviesFragment : Fragment() {

    private lateinit var movies: List<Result>

    val movieDisposable: LifeCycleDisposable = LifeCycleDisposable(this)
    private var recyclerView: RecyclerView? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var swipeRefreshLayout: SwipeRefreshLayout? = null
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

    private var popularMoviesViewModel: PopularMoviesViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpActionBar()
        setUpRecyclerView()
        setUpSwipeView()
        showPopularMovies()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentPopularMoviesBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_popular_movies, container, false)
        popularMoviesViewModel = ViewModelProviders.of(this).get(PopularMoviesViewModel::class.java)
        return binding.root
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

    @SuppressLint("ResourceAsColor")
    private fun setUpSwipeView() {
        swipeRefreshLayout = swipe
        swipeRefreshLayout?.setColorSchemeColors(R.color.colorPrimary)
        swipeRefreshLayout?.setOnRefreshListener {
            showPopularMovies()
        }
    }

    fun hideLoader() {
        progressBar.visibility = View.GONE
    }

    private fun showPopularMovies() {
        popularMoviesViewModel?.getPopularMovies()?.observe(viewLifecycleOwner, Observer {
            hideLoader()
            movies = it
            swipeRefreshLayout?.isRefreshing = false
            movieAdapter.setData(it)
        })
    }

    private fun setUpActionBar() {
        val actionBar = (activity as AppCompatActivity?)!!.supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(false)
        actionBar?.title = "Upcomming Movies"
    }
}