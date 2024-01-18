package com.example.movies

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@Entity(tableName = "favourite_movies")
@JsonClass(generateAdapter = true)
data class Movie(
    @PrimaryKey @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "description") val description: String,
    @Json(name = "year") val year: Int,
    @Embedded @Json(name = "poster") val poster: Poster,
    @Embedded @Json(name = "rating") val rating: Rating
) : Serializable
