package com.prueba.francisco.retrofitmoviesexample.upcomingMovies.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prueba.francisco.retrofitmoviesexample.R
import com.prueba.francisco.retrofitmoviesexample.upcomingMovies.viewmodel.UpcomingMoviesViewModel
import com.prueba.francisco.retrofitmoviesexample.util.LifeCycleDisposable
import kotlinx.android.synthetic.main.fragment_upcoming.*

class UpcomingFragment : Fragment() {

    val lifeCycleDisposable = LifeCycleDisposable(this)
    private var recyclerView: RecyclerView? = null
    private var upcomingMoviesViewModel: UpcomingMoviesViewModel? = null
    private val movieAdapter by lazy {
        UpcomingMoviesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        upcomingMoviesViewModel =
            ViewModelProviders.of(this).get(UpcomingMoviesViewModel::class.java)
        return inflater.inflate(R.layout.fragment_upcoming, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpRecycler()
        setUpActionBar()
        showMovies()
    }

    private fun showMovies() {
        upcomingMoviesViewModel?.getUpcomingMovies()?.observe(viewLifecycleOwner, Observer {
            movieAdapter.setData(it)
        })
    }

    fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun setUpRecycler() {
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView = rvUpcoming
        recyclerView?.layoutManager = layoutManager
        recyclerView?.setHasFixedSize(true)
        recyclerView?.itemAnimator = DefaultItemAnimator()
        recyclerView?.adapter = movieAdapter
    }

    private fun setUpActionBar() {
        val actionBar = (activity as AppCompatActivity?)!!.supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(false)
        actionBar?.title = "Upcomming Movies"
    }
}