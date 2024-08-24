package com.example.theanimeapp.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.theanimeapp.Model.Data
import com.example.theanimeapp.R

class AnimeAdapter(
    private val animeList: List<Data>,
    private val itemOnClick: (Data) -> Unit
) : RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder>() {

    inner class AnimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val genereTextView: TextView = itemView.findViewById(R.id.genere)
        private val titleTextView: TextView = itemView.findViewById(R.id.texv)
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)
        private val rankingTextView: TextView = itemView.findViewById(R.id.ranking)

        fun bind(anime: Data) {
            titleTextView.text = anime.title
            genereTextView.text = "Genre: ${anime.genres.joinToString(", ")}"
            rankingTextView.text = "Ranking: ${anime.ranking.toString()}"
            Glide.with(itemView.context).load(anime.thumb).into(imageView)

            itemView.setOnClickListener { itemOnClick(anime) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_anime, parent, false)
        return AnimeViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        holder.bind(animeList[position])
    }

    override fun getItemCount(): Int = animeList.size
}
