package com.ahmadasrori.tmdb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahmadasrori.tmdb.data.model.ResultsItemReview
import com.ahmadasrori.tmdb.databinding.ItemReviewBinding

class ReviewAdapter(
    private val items: List<ResultsItemReview>
) : RecyclerView.Adapter<ReviewAdapter.ItemVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVH {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemVH(binding)
    }

    override fun onBindViewHolder(holder: ItemVH, position: Int) {
        val item = items[position]
        holder.binding.tvName.text = item.author
        holder.binding.tvContent.text = item.content
        holder.binding.tvCreatedAt.text = item.createdAt
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ItemVH(mainBinding: ItemReviewBinding) :
        RecyclerView.ViewHolder(mainBinding.root) {
        val binding = mainBinding
    }

}