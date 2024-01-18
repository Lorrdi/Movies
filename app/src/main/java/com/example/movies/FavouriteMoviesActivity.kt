package com.example.movies

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FavouriteMoviesActivity : AppCompatActivity() {

    companion object {
        fun newIntent(ctx: Context): Intent {
            return Intent(ctx, FavouriteMoviesActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite_movies)

        val recyclerViewFavouriteMovies: RecyclerView =
            findViewById(R.id.recyclerViewFavouriteMovies)
        val favouriteMoviesAdapter = MoviesAdapter()
        recyclerViewFavouriteMovies.layoutManager = GridLayoutManager(this, 2)
        recyclerViewFavouriteMovies.adapter = favouriteMoviesAdapter

        favouriteMoviesAdapter.setOnMovieClickListener(object : MoviesAdapter.OnMovieClickListener {
            override fun onMovieClick(movie: Movie) {
                startActivity(ActivityMovieDetail.newIntent(this@FavouriteMoviesActivity, movie))
            }

        })

        val viewModel = ViewModelProvider(this)[FavouriteMoviesViewModel::class.java]

        viewModel.getMovies().observe(this) {
            favouriteMoviesAdapter.setMovies(it)
        }

    }
}