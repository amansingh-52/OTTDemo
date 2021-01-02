package com.aman.ottdemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import okhttp3.Callback

class GenreAdaptor(private val list: ArrayList<GenreDataClass>,
                   private val listener: OnItemClickListener
): RecyclerView.Adapter<GenreAdaptor.GenreAdaptorVH> (){




    inner class GenreAdaptorVH (itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val genre: TextView = itemView.findViewById(R.id.genreTextView)
        init {
                itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.onItemCliCk(genre.text.toString())
        }
    }

    interface OnItemClickListener{
        fun onItemCliCk(string: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreAdaptorVH {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.genre_card_view,
            parent,false)

        return GenreAdaptorVH(itemView)
    }

    override fun onBindViewHolder(holder: GenreAdaptorVH, position: Int) {
        val currentItem = list[position]
        holder.genre.text = currentItem.genre
    }

    override fun getItemCount(): Int = list.size


}