package com.example.movies

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ActivityMovieDetail : AppCompatActivity() {

    private lateinit var imageViewPoster: ImageView
    private lateinit var imageViewStar: ImageView

    private lateinit var textViewTitle: TextView
    private lateinit var textViewYear: TextView
    private lateinit var textViewDescription: TextView
    private lateinit var viewModel: MovieDetailViewModel

    private lateinit var recyclerViewTrailers: RecyclerView
    private lateinit var recyclerViewReviews: RecyclerView
    private lateinit var trailersAdapter: TrailersAdapter
    private lateinit var reviewsAdapter: ReviewsAdapter

    companion object {
        private const val EXTRA_MOVIE = "movie"

        fun newIntent(ctx: Context, movie: Movie): Intent {
            val intent = Intent(ctx, ActivityMovieDetail::class.java)
            intent.putExtra(EXTRA_MOVIE, movie)
            return intent
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        initViews()

        reviewsAdapter = ReviewsAdapter()
        recyclerViewReviews.adapter = reviewsAdapter
        trailersAdapter = TrailersAdapter()
        recyclerViewTrailers.adapter = trailersAdapter

        viewModel = ViewModelProvider(this)[MovieDetailViewModel::class.java]

        val movie = intent.getSerializableExtra(EXTRA_MOVIE, Movie::class.java)

        Glide.with(this).load(movie?.poster?.url).into(imageViewPoster)
        textViewTitle.text = movie?.name
        textViewYear.text = movie?.year.toString()
        textViewDescription.text = movie?.description

        viewModel.loadTrailers(movie!!.id)
        viewModel.loadReviews(movie.id)

        viewModel.getReviews().observe(this) {
            reviewsAdapter.setReviews(it)
        }
        val starOff = ContextCompat.getDrawable(
            this, android.R.drawable.star_big_off
        )
        val starOn = ContextCompat.getDrawable(
            this, android.R.drawable.star_big_on
        )

        viewModel.getFavouriteMovie(movie.id).observe(this) { movieFromDB ->

            if (movieFromDB == null) {
                imageViewStar.setImageDrawable(starOff)
                imageViewStar.setOnClickListener {
                    viewModel.insertMovie(movie)
                }

            } else {
                imageViewStar.setImageDrawable(starOn)
                imageViewStar.setOnClickListener {
                    viewModel.removeMovie(movie.id)
                }
            }

        }

        viewModel.getTrailers().observe(this) {
            trailersAdapter.setTrailers(it)
        }
        trailersAdapter.setOnTrailerClickListener(object : TrailersAdapter.OnTrailerClickListener {
            override fun onTrailerClick(trailer: Trailer?) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(trailer!!.url)
                startActivity(intent)
            }
        })


    }

    private fun initViews() {
        imageViewPoster = findViewById(R.id.movieDetailPoster)
        textViewTitle = findViewById(R.id.textViewTitle)
        textViewYear = findViewById(R.id.textViewYear)
        textViewDescription = findViewById(R.id.textViewDescription)
        recyclerViewReviews = findViewById(R.id.recyclerViewReviews)
        recyclerViewTrailers = findViewById(R.id.recyclerViewTrailers)
        imageViewStar = findViewById(R.id.imageViewStar)
    }

}
