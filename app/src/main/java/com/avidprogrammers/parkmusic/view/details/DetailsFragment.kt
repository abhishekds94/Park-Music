package com.avidprogrammers.parkmusic.view.details

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.avidprogrammers.parkmusic.MainActivity
import com.avidprogrammers.parkmusic.R
import com.avidprogrammers.parkmusic.databinding.FragmentDetailsBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val args by navArgs<DetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as MainActivity).supportActionBar?.title = ""

        val binding = FragmentDetailsBinding.bind(view)

        binding.apply {
            val search = args.search

            Glide.with(this@DetailsFragment)
                .load(search.artworkUrl100)
                .error(R.drawable.ic_error)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        return false
                    }
                })
                .into(imageView)

            val trackTimeVal = search.trackTimeMillis!!.toLong()
            val minutes = DateTimeFormatter.ofPattern("mm : ss")
                .withZone(ZoneId.of("UTC"))
                .format(Instant.ofEpochMilli(trackTimeVal))

            val parsedDate =
                LocalDateTime.parse(search.releaseDate, DateTimeFormatter.ISO_DATE_TIME)
            val formattedDate = parsedDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))

            trackName.text = search.trackName ?: "N/A"
            trackTime.text = minutes!!.toString()
            trackDate.text = formattedDate!!.toString()
            trackArtist.text = search.artistName ?: "N/A"
            trackGenre.text = search.primaryGenreName ?: "N/A"
            trackLink.text = search.previewUrl ?: "N/A"
            trackShortDesc.text = search.shortDescription ?: "N/A"
            trackLongDesc.text = search.longDescription ?: "N/A"


            val uri = Uri.parse(search.previewUrl)
            val intent = Intent(Intent.ACTION_VIEW, uri)

            trackLink.apply {
                text = search.previewUrl
                setOnClickListener {
                    context.startActivity(intent)
                }
                paint.isUnderlineText = true
            }
        }
    }
}