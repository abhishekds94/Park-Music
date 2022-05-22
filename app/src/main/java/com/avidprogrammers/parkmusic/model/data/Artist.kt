package com.avidprogrammers.parkmusic.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Artist(
    val artistName: String,
    val artworkUrl100: String
) : Parcelable