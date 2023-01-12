package com.ahmadasrori.tmdb.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ahmadasrori.tmdb.data.model.ResultItem
import com.ahmadasrori.tmdb.databinding.ItemMovieBinding
import com.ahmadasrori.tmdb.helper.setImageUrl
import com.ahmadasrori.tmdb.helper.timeStamptoString

class MovieAdapter(
    private val onItemClick: (movie: ResultItem) -> Unit
): PagingDataAdapter<ResultItem, MovieAdapter.MovieViewHolder>(DIFF_CALLBACK) {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ResultItem>() {
            override fun areItemsTheSame(oldItem: ResultItem, newItem: ResultItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ResultItem, newItem: ResultItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapter.MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieAdapter.MovieViewHolder, position: Int) {
        getItem(position)?.let { story ->
            holder.bind(story, position)
        }
    }

    inner class MovieViewHolder(private val binding: ItemMovieBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(movie: ResultItem, position: Int) {
            binding.apply {
                tvName.text = movie.title+position
                tvDate.text = "release date "+movie.releaseDate.timeStamptoString()
                ivThumbnail.setImageUrl("http://image.tmdb.org/t/p/w500"+movie.posterPath)
                root.setOnClickListener {
                    onItemClick(movie)
                }
            }
        }
    }

}