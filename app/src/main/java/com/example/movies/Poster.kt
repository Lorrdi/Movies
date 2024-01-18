package com.example.movies

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class Poster(@Json(name = "url") val url: String): Serializable