package com.avidprogrammers.parkmusic.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.avidprogrammers.parkmusic.R
import com.avidprogrammers.parkmusic.databinding.ItemSongsBinding
import com.avidprogrammers.parkmusic.model.data.Songs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class SongsAdapter(
    private val onClickListener: OnItemClickListener
) : PagingDataAdapter<Songs, SongsAdapter.SongsViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongsViewHolder {
        val binding =
            ItemSongsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return SongsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongsViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class SongsViewHolder(private val binding: ItemSongsBinding) :
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

        fun bind(item: Songs) {
            binding.apply {
                Glide.with(itemView)
                    .load(item.artworkUrl100)
                    .fitCenter()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(imageView)
                textViewSongName.text = item.trackName
                textViewUserName.text = item.artistName
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(item: Songs)
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Songs>() {
            override fun areItemsTheSame(oldItem: Songs, newItem: Songs) =
                oldItem.trackId == newItem.trackId

            override fun areContentsTheSame(oldItem: Songs, newItem: Songs) =
                oldItem == newItem
        }
    }

}