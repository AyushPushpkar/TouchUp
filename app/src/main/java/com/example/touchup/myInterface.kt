package com.example.touchup

import retrofit2.http.GET

interface myInterface {

    @GET("/quotes")    //api path
    suspend fun getQuotes() : retrofit2.Response<QuoteList> // call api

}