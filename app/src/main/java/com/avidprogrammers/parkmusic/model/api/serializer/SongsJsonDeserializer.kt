package com.avidprogrammers.parkmusic.model.api.serializer

import com.avidprogrammers.parkmusic.model.api.ArtistsSearchResponse
import com.avidprogrammers.parkmusic.model.api.SongsSearchResponse
import com.avidprogrammers.parkmusic.model.data.Artist
import com.avidprogrammers.parkmusic.model.data.Songs
import com.google.gson.*
import java.lang.Exception
import java.lang.reflect.Type

class SongsJsonDeserializer: JsonDeserializer<SongsSearchResponse> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): SongsSearchResponse {
            return SongsSearchResponse(
                try {
                val jsonObject: JsonObject = json!!.asJsonObject

                val resultJson = jsonObject.get("results").asJsonArray
                val result = arrayListOf<Songs>()
                resultJson.forEach {
                    result.add(it.asJsonObject.toSongs())
                }
                result
            } catch (e: Exception) {
                emptyList()
            }
        )
    }

    private fun JsonObject.toSongs(): Songs {
        return Songs(
            get("trackId").asInt,
            get("artistName").asString,
            get("trackName").asString,
            get("previewUrl").asString,
            get("artworkUrl100").asString,
            get("releaseDate").asString,
            get("trackTimeMillis").asInt,
            get("primaryGenreName").asString,
            get("shortDescription").asString,
            get("longDescription").asString
        )
    }

}