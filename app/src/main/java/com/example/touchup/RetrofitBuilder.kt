package com.example.touchup

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    val api = "https://quotable.io/"

    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(api)                    //api path
            .addConverterFactory(GsonConverterFactory.create())     // convert response from gson to java object
            .build()

    }
}