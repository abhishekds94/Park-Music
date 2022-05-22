package com.avidprogrammers.parkmusic.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Songs(
    val trackId: Int,
    val artistName: String,
    val trackName: String,
    val previewUrl: String,
    val artworkUrl100: String,
    val releaseDate: String,
    val trackTimeMillis: Int,
    val primaryGenreName: String,
    val shortDescription: String,
    val longDescription: String
) : Parcelable