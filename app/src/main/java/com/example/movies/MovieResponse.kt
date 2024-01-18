package com.example.movies

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class MovieResponse(@Json(name = "docs") val movies: MutableList<Movie>) {
    override fun toString(): String {
        return "MovieResponse(movies=$movies)"
    }
}

