package com.avidprogrammers.parkmusic.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItunesSearch(
    val trackId: Int?,
    val artistName: String?,
    val collectionName: String?,
    val trackName: String?,
    val trackTimeMillis: String?,
    val collectionArtistId: Int?,
    val previewUrl: String?,
    val artworkUrl30: String?,
    val artworkUrl60: String?,
    val artworkUrl100: String?,
    val releaseDate: String?,
    val country: String?,
    val primaryGenreName: String?,
    val shortDescription: String? = "N/A",
    val longDescription: String? = "N/A"
) : Parcelable {
}