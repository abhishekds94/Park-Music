package com.avidprogrammers.parkmusic.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.avidprogrammers.parkmusic.R
import com.avidprogrammers.parkmusic.databinding.ItemArtistsBinding
import com.avidprogrammers.parkmusic.model.data.Artist
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class ArtistsAdapter(
    private val onClickListener: OnItemClickListener
) : PagingDataAdapter<Artist, ArtistsAdapter.ArtistViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val binding =
            ItemArtistsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ArtistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class ArtistViewHolder(private val binding: ItemArtistsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        onClickListener.onItemClick(item)
                    }
                }
            }
        }

        fun bind(item: Artist) {
            binding.apply {
                Glide.with(itemView)
                    .load(item.artworkUrl100)
                    .fitCenter()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(imageView)
                artistName.text = item.artistName
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(item: Artist)
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Artist>() {
            override fun areItemsTheSame(oldItem: Artist, newItem: Artist) =
                oldItem.artistName == newItem.artistName

            override fun areContentsTheSame(oldItem: Artist, newItem: Artist) =
                oldItem == newItem
        }
    }

}