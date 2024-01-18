package com.example.movies

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MovieDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG = "MovieDetailActivity"
    private val trailers: MutableLiveData<List<Trailer>> = MutableLiveData()
    private val reviews: MutableLiveData<List<Review>> = MutableLiveData()
    private val compositeDisposable = CompositeDisposable()
    private val movieDao: MovieDao

    init {
        movieDao = MovieDatabase.getInstance(application).movieDao()
    }

    fun getFavouriteMovie(movieId: Int): LiveData<Movie> {
        return movieDao.getFavouriteMovie(movieId)
    }

    fun getTrailers(): LiveData<List<Trailer>> {
        return trailers
    }

    fun getReviews(): LiveData<List<Review>> {
        return reviews
    }

    fun insertMovie(movie: Movie) {
        compositeDisposable.add(
            movieDao.insertMovie(movie).subscribeOn(Schedulers.io()).subscribe()
        )
    }

    fun removeMovie(movieId: Int) {
        compositeDisposable.add(
            movieDao.removeMovie(movieId).subscribeOn(Schedulers.io()).subscribe()
        )
    }

    fun loadTrailers(id: Int) {
        compositeDisposable.add(ApiFactory.apiService.loadTrailers(id).subscribeOn(
            Schedulers.io()
        ).observeOn(
            AndroidSchedulers.mainThread()
        ).map {
            return@map it.extractTrailers()
        }.subscribe({
            trailers.value = it
        }, {
            Log.d(TAG, it.toString())
        })
        )
    }

    fun loadReviews(movieId: Int) {
        compositeDisposable.add(ApiFactory.apiService.loadReviews(movieId).subscribeOn(
            Schedulers.io()
        ).observeOn(
            AndroidSchedulers.mainThread()
        ).map { return@map it.reviews }.subscribe({
                reviews.value = it
                Log.d(TAG, it.toString())
            },

                { Log.d(TAG, it.toString()) })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}