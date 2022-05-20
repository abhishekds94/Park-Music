package com.avidprogrammers.parkmusic.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.avidprogrammers.parkmusic.R
import com.avidprogrammers.parkmusic.databinding.ItemItunesSearchBinding
import com.avidprogrammers.parkmusic.model.data.ItunesSearch

class ItunesSearchAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<ItunesSearch, ItunesSearchAdapter.SearchViewHolder>(SEARCH_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding =
            ItemItunesSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class SearchViewHolder(private val binding: ItemItunesSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener.onItemClick(item)
                    }
                }
            }
        }

        fun bind(search: ItunesSearch) {
            binding.apply {
                Glide.with(itemView)
                    .load(search.artworkUrl100)
                    .fitCenter()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(imageView)

                textViewSongName.text = search.trackName
                textViewUserName.text = search.artistName
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(search: ItunesSearch)
    }

    companion object {
        private val SEARCH_COMPARATOR = object : DiffUtil.ItemCallback<ItunesSearch>() {
            override fun areItemsTheSame(oldItem: ItunesSearch, newItem: ItunesSearch) =
                oldItem.trackId == newItem.trackId

            override fun areContentsTheSame(oldItem: ItunesSearch, newItem: ItunesSearch) =
                oldItem == newItem
        }
    }
}