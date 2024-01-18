package com.example.movies


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Review(
    @Json(name = "author") val author: String?,
    @Json(name = "review") val review: String?,
    @Json(name = "type") val type: String?
) {
    override fun toString(): String {
        return "Review(author='$author', review='$review', type='$type')"
    }
}