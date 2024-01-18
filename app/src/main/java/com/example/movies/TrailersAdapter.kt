package com.example.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TrailersAdapter : RecyclerView.Adapter<TrailersAdapter.TrailersViewHolder>() {
    private var trailers: List<Trailer> = ArrayList()
    private var onTrailerClickListener: OnTrailerClickListener? = null
    fun setTrailers(trailers: List<Trailer>) {
        this.trailers = trailers
        notifyDataSetChanged()
    }

    fun setOnTrailerClickListener(onTrailerClickListener: OnTrailerClickListener?) {
        this.onTrailerClickListener = onTrailerClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.trailer_item, parent, false
        )
        return TrailersViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrailersViewHolder, position: Int) {
        val trailer = trailers[position]
        holder.textViewTrailerName.text = trailer.name
        holder.itemView.setOnClickListener {

                onTrailerClickListener?.onTrailerClick(trailer)

        }
    }

    override fun getItemCount(): Int {
        return trailers.size
    }

    interface OnTrailerClickListener {
        fun onTrailerClick(trailer: Trailer?)
    }

    class TrailersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTrailerName: TextView

        init {
            textViewTrailerName = itemView.findViewById(R.id.textViewTrailerName)
        }
    }
}