package com.prueba.francisco.retrofitmoviesexample.upcomingMovies.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prueba.francisco.retrofitmoviesexample.R
import com.prueba.francisco.retrofitmoviesexample.upcomingMovies.UpcomingMoviesContract
import com.prueba.francisco.retrofitmoviesexample.upcomingMovies.data.model.Results
import com.prueba.francisco.retrofitmoviesexample.upcomingMovies.data.model.UpcomingModel
import com.prueba.francisco.retrofitmoviesexample.upcomingMovies.presenter.UpcomingMoviesPresenter
import com.prueba.francisco.retrofitmoviesexample.util.LifeCycleDisposable
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_upcoming.*

class UpcomingFragment : Fragment(), UpcomingMoviesContract.View {

    val lifeCycleDisposable = LifeCycleDisposable(this)
    private var upcomingMoviesPresenter: UpcomingMoviesContract.Presenter? = null
    var recyclerView: RecyclerView? = null
    private val movieAdapter by lazy {
        UpcomingMoviesAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_upcoming, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpRecycler()
        upcomingMoviesPresenter = UpcomingMoviesPresenter(this)
        val actionBar = (activity as AppCompatActivity?)!!.supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(false)
        actionBar?.title = "Upcomming Movies"
        upcomingMoviesPresenter?.getUpcomingMovies()
    }

    override fun showLoader() {
    }

    override fun hideLoader() {
    }

    override fun showMovies(movies: Single<UpcomingModel>) {
        movies.observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<UpcomingModel> {
                override fun onSuccess(movies: UpcomingModel?) {
                    if (movies != null)
                        showRecycler(movies.results)
                }

                override fun onSubscribe(disposable: Disposable?) {
                    if (disposable != null)
                        lifeCycleDisposable.add(disposable)
                }

                override fun onError(e: Throwable?) {
                    showMessage("The is an error in the api request")
                }
            })
    }

    fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun setUpRecycler(){
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView = rvUpcoming
        recyclerView?.layoutManager = layoutManager
        recyclerView?.setHasFixedSize(true)
        recyclerView?.itemAnimator = DefaultItemAnimator()
        recyclerView?.adapter = movieAdapter
    }

    fun showRecycler(listMovies: List<Results>) {
        movieAdapter.setData(listMovies)
    }
}