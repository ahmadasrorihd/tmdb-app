package com.ahmadasrori.tmdb.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahmadasrori.tmdb.data.model.ResultsItemTrailer
import com.ahmadasrori.tmdb.databinding.ItemTrailerBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions


class TrailerAdapter (
    private val activity: Activity,
    private val items: List<ResultsItemTrailer>,
    private val onItemClick: (position: Int) -> Unit
) : RecyclerView.Adapter<TrailerAdapter.ItemVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVH {
        val binding = ItemTrailerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemVH(binding)
    }

    override fun onBindViewHolder(holder: ItemVH, position: Int) {
        val item = items[position]
        holder.binding.tvName.text = item.name
        holder.binding.tvSite.text = item.site
        holder.binding.cardItem.setOnClickListener {
            onItemClick(position)
        }
        Glide.with(activity)
            .load("https://img.youtube.com/vi/"+item.key+"/0.jpg")
            .transition(DrawableTransitionOptions.withCrossFade())
            .centerCrop()
            .into(holder.binding.ivTrailer)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ItemVH(mainBinding: ItemTrailerBinding) :
        RecyclerView.ViewHolder(mainBinding.root) {
        val binding = mainBinding
    }

}