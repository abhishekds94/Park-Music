package com.avidprogrammers.parkmusic.model.api.serializer

import com.avidprogrammers.parkmusic.model.api.ArtistsSearchResponse
import com.avidprogrammers.parkmusic.model.data.Artist
import com.google.gson.*
import java.lang.Exception
import java.lang.reflect.Type

class ArtistJsonDeserializer : JsonDeserializer<ArtistsSearchResponse> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): ArtistsSearchResponse {
        return ArtistsSearchResponse(
            try {
                val jsonObject: JsonObject = json!!.asJsonObject

                val resultJson = jsonObject.get("results").asJsonArray
                val result = arrayListOf<Artist>()
                resultJson.forEach {
                    result.add(it.asJsonObject.toArtist())
                }
                result
            } catch (e: Exception) {
                emptyList()
            }
        )
    }

    private fun JsonObject.toArtist(): Artist {
        return Artist(
            get("artistName").asString,
            get("artworkUrl100").asString
        )
    }

}