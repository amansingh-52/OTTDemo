package com.aman.ottdemo

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class RVAdapter (private val list: ArrayList<OTTProperties>): RecyclerView.Adapter<RVAdapter.RVViewHolder>() {

    class RVViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val imdbRatings : TextView = itemView.findViewById(R.id.imdbRatingsTextView)
        val synopsis : TextView = itemView.findViewById(R.id.synopsisTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_card,
        parent,false)
        return RVViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RVViewHolder, position: Int) {
        val currentItem = list[position]
        holder.imdbRatings.text = "IMDb ratings: ${currentItem.imdbrating}"
        holder.titleTextView.text = currentItem.title
        holder.synopsis.text = ""
        Picasso.get().load(currentItem.imageUrl).into(holder.imageView)
    }

    override fun getItemCount(): Int = list.size


}