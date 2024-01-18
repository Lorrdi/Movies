package com.example.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {
    private var movies: List<Movie> = ArrayList()
    private lateinit var onReachEndListener: OnReachEndListener
    private lateinit var onMovieClickListener: OnMovieClickListener
    fun setMovies(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    fun setOnReachEndListener(onReachEndListener: OnReachEndListener) {
        this.onReachEndListener = onReachEndListener
    }

    fun setOnMovieClickListener(onMovieClickListener: OnMovieClickListener) {
        this.onMovieClickListener = onMovieClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.movie_item, parent, false
        )

        return MoviesViewHolder(view)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movie = movies[position]
        val rating = movie.rating.kp
        Glide.with(holder.itemView).load(movie.poster.url).error(R.drawable.circle_red)
            .into(holder.imageViewPoster)

        val backgroundId = if (rating > 7) {
            R.drawable.circle_green
        } else if (7 > rating && rating > 4) {
            R.drawable.circle_orange
        } else R.drawable.circle_red

        holder.textViewRating.background =
            ContextCompat.getDrawable(holder.itemView.context, backgroundId)
        holder.textViewRating.text = String.format("%.1f", rating)
        if (position >= movies.size - 10 && ::onReachEndListener.isInitialized) {
            onReachEndListener.onReachEnd()
        }
        holder.itemView.setOnClickListener {
            if (::onMovieClickListener.isInitialized) {
                onMovieClickListener.onMovieClick(movie)
            }
        }
    }

    override fun getItemCount(): Int = movies.size

    interface OnReachEndListener {
        fun onReachEnd()
    }

    interface OnMovieClickListener {
        fun onMovieClick(movie: Movie)
    }

    class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val imageViewPoster: ImageView
        internal var textViewRating: TextView

        init {
            imageViewPoster = itemView.findViewById(R.id.imageViewPoster)
            textViewRating = itemView.findViewById(R.id.textViewRating)
        }
    }
}
