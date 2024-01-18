package com.example.movies

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class FavouriteMoviesViewModel(application: Application) : AndroidViewModel(application) {

    private val movieDao: MovieDao

    init {
        movieDao = MovieDatabase.getInstance(application).movieDao()
    }

    fun getMovies() : LiveData<List<Movie>> {
        return movieDao.getAllFavouriteMovies()
    }
}