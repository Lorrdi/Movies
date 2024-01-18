package com.example.movies

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel() : ViewModel() {

    private val TAG = "MainViewModel"

    val movies: MutableLiveData<MutableList<Movie>> = MutableLiveData()
    private val isLoading = MutableLiveData(false)

    private val compositeDisposable = CompositeDisposable()

    private var page = 1

    init {
        loadMovies()
    }

    fun getMovies(): LiveData<MutableList<Movie>> {
        return movies
    }

    fun getIsLoading(): LiveData<Boolean> {
        return isLoading
    }

    fun loadMovies() {
        val loading = isLoading.value
        if (loading != null && loading) {
            return
        }
        compositeDisposable.add(
            ApiFactory.apiService.loadMovies(page).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { isLoading.setValue(true) }
                .doAfterTerminate { isLoading.setValue(false) }.subscribe({
                    val loadedMovies: MutableList<Movie>? = movies.value
                    if (loadedMovies != null) {
                        loadedMovies.addAll(it.movies)
                        movies.setValue(loadedMovies)
                    } else {
                        movies.setValue(it.movies.toMutableList())
                    }
                    Log.d(TAG, "Loaded: $page")
                    page++
                }, { Log.d(TAG, it.toString()) })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}