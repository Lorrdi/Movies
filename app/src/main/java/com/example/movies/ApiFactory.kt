package com.example.movies

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class ApiFactory {
    companion object {
        private const val BASE_URL = "https://api.kinopoisk.dev/"
        private val retrofit =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create()).build()
        val apiService: ApiService = retrofit.create(ApiService::class.java)
    }
}