package com.example.movies


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReviewResponse(
    @Json(name = "docs") val reviews: List<Review>
) {
    override fun toString(): String {
        return "ReviewResponse(reviews=$reviews)"
    }
}