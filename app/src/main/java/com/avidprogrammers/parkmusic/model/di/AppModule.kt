package com.avidprogrammers.parkmusic.model.di

import com.avidprogrammers.parkmusic.model.api.ApiConstants
import com.avidprogrammers.parkmusic.model.api.ArtistsSearchApi
import com.avidprogrammers.parkmusic.model.api.ArtistsSearchResponse
import com.avidprogrammers.parkmusic.model.api.ItunesSearchApi
import com.avidprogrammers.parkmusic.model.api.serializer.ArtistJsonDeserializer
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL_ITUNES)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    @Named("artists")
    fun provideTopArtistsSearchApi(): Retrofit {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapter(ArtistsSearchResponse::class.java, ArtistJsonDeserializer())
        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL_TOP_WEEKLY)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
            .build()
    }

    @Provides
    @Singleton
    fun provideArtistsSearchApi(@Named("artists") retrofit: Retrofit): ArtistsSearchApi =
        retrofit.create(ArtistsSearchApi::class.java)

    @Provides
    @Singleton
    fun provideItunesApi(retrofit: Retrofit): ItunesSearchApi =
        retrofit.create(ItunesSearchApi::class.java)


}