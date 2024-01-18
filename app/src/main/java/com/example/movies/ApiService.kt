package com.example.movies

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v1.3/movie?token=NXVZTM7-2EYM4GY-GZHKHN6-NTAT592&sortField=votes.kp&sortType=-1&limit=100&year=1990-2023&rating.kp=7-10")
    fun loadMovies(@Query("page") page: Int): Single<MovieResponse>

    @GET("v1.3/movie?token=NXVZTM7-2EYM4GY-GZHKHN6-NTAT592&selectFields=videos")
    fun loadTrailers(@Query("id") id: Int): Single<TrailersResponse>

    @GET("v1/review?token=NXVZTM7-2EYM4GY-GZHKHN6-NTAT592")
    fun loadReviews(@Query("movieId") movieId: Int): Single<ReviewResponse>
}