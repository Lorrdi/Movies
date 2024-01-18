package com.example.movies


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TrailersResponse(
    @Json(name = "docs") val docs: List<Doc>
) {
    fun extractTrailers(): List<Trailer> {
        return docs[0].videos.trailers
    }
}