package com.example.movies

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var progressBarLoading: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBarLoading = findViewById(R.id.progressBarLoading)
        recyclerView = findViewById(R.id.recyclerViewMovies)
        moviesAdapter = MoviesAdapter()

        recyclerView.adapter = moviesAdapter
        recyclerView.layoutManager = GridLayoutManager(this, 2)


        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.getMovies().observe(this) { movies -> moviesAdapter.setMovies(movies) }
        viewModel.getIsLoading().observe(this) { isLoading ->
            if (isLoading) {
                progressBarLoading.visibility = View.VISIBLE
            } else {
                progressBarLoading.visibility = View.GONE
            }
        }
        moviesAdapter.setOnReachEndListener(object : MoviesAdapter.OnReachEndListener {
            override fun onReachEnd() {
                viewModel.loadMovies()
            }
        })
        moviesAdapter.setOnMovieClickListener(object : MoviesAdapter.OnMovieClickListener {

            override fun onMovieClick(movie: Movie) {
                val intent = ActivityMovieDetail.newIntent(this@MainActivity, movie)
                startActivity(intent)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.favouriteMovies) startActivity(
            FavouriteMoviesActivity.newIntent(
                this
            )
        )
        return super.onOptionsItemSelected(item)
    }
}