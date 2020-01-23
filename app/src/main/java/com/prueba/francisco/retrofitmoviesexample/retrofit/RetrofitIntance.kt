package com.prueba.francisco.retrofitmoviesexample.retrofit

import com.prueba.francisco.retrofitmoviesexample.service.MovieDataService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitIntance {

    val BASE_URL = "https://api.themoviedb.org/3/"
    var retrofit: Retrofit? = null

    fun getService(): MovieDataService{
        if (retrofit == null){
            retrofit = Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!.create(MovieDataService::class.java)
    }
}