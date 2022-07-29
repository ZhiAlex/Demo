package com.zhialex.demo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zhialex.demo.models.Film

class FilmAdapter(private val values: List<Film>, val listener: OnItemClickListener) :
    RecyclerView.Adapter<FilmAdapter.FilmHolder>() {

    interface OnItemClickListener{
        fun onItemClick(filmHolder: FilmHolder, film: Film)
    }

    class FilmHolder(item: View, val context: Context) : RecyclerView.ViewHolder(item) {
        var textView: TextView
        var imgView: ImageView

        init {
            textView = item.findViewById(R.id.filmTitle)
            imgView = item.findViewById(R.id.filmImg)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.film_item, parent, false)
        return FilmHolder(itemView, parent.context)
    }

    override fun onBindViewHolder(holder: FilmHolder, position: Int) {
        holder.textView.text = values[position].title
        Glide
            .with(holder.itemView.context)
            .load(values[position].imgUrl)
            .into(holder.imgView)
        holder.itemView.setOnClickListener{
            listener.onItemClick(holder, values[position])
        }
    }

    override fun getItemCount() = values.size
};