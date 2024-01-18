package com.example.movies

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class Rating(@Json(name="kp") val kp: Double): Serializable
