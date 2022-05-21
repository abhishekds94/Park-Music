package com.avidprogrammers.parkmusic.model.di

import com.avidprogrammers.parkmusic.model.api.ItunesSearchApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(ItunesSearchApi.BASE_URL_ITUNES)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideItunesApi(retrofit: Retrofit): ItunesSearchApi =
        retrofit.create(ItunesSearchApi::class.java)

}