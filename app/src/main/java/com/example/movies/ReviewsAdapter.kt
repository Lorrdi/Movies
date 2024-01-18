package com.example.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class ReviewsAdapter : RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder>() {

    private var reviews: List<Review> = ArrayList()


    fun setReviews(reviews: List<Review>) {
        this.reviews = reviews
        notifyDataSetChanged()
    }

    class ReviewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val reviewsLayout: LinearLayout?
        val textViewAuthor: TextView?
        val textViewReview: TextView?

        init {
            itemView.let {
                textViewReview = it.findViewById(R.id.textViewReviewText)
                textViewAuthor = it.findViewById(R.id.textViewReviewAuthor)
                reviewsLayout = it.findViewById(R.id.reviewsLayout)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.review_item, parent, false
        )
        return ReviewsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    override fun onBindViewHolder(holder: ReviewsViewHolder, position: Int) {
        val review = reviews[position]
        val type = review.type
        holder.textViewReview?.text = review.review
        holder.textViewAuthor?.text = review.author

        val colorId = when (type) {
            "Позитивный" -> android.R.color.holo_green_light
            "Негативный" -> android.R.color.holo_red_light
            else -> {
                android.R.color.holo_red_light
            }
        }

        holder.reviewsLayout?.setBackgroundColor(
            ContextCompat.getColor(
                holder.itemView.context,
                colorId
            )
        )
    }
}