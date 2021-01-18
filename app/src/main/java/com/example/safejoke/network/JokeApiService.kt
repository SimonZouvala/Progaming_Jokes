package com.example.safejoke.network

import com.example.safejoke.domain.Joke
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.http.GET

//https://github.com/15Dkatz/official_joke_api

private const val BASE_URL = "https://official-joke-api.appspot.com/jokes/programming/"


interface JokeApiService {

    @GET("random")
    suspend fun getProperties(): JokeApiProperties
}

object JokeApi {
    /**
     * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
     * full Kotlin compatibility.
     */
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    /**
     * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
     * object.
     */
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

    val jokeNetwork: JokeApiService by lazy { retrofit.create(JokeApiService::class.java) }

}