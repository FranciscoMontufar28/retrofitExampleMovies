package com.prueba.francisco.retrofitmoviesexample.retrofit

import com.prueba.francisco.retrofitmoviesexample.service.MovieDataService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private var retrofit: Retrofit? = null

    fun getService(): MovieDataService{
        if (retrofit == null){
            retrofit = Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!.create(MovieDataService::class.java)
    }
}