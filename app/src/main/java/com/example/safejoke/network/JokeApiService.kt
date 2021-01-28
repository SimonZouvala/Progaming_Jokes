package com.example.safejoke.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://official-joke-api.appspot.com/jokes/programming/"

/**
 * A public interface that exposes the [getProperties] method.
 * For API is used https://official-joke-api.appspot.com/jokes/programming/
 *
 * More information about API jokes here: https://github.com/15Dkatz/official_joke_api
 */
interface JokeApiService {
    /**
     * Returns a Coroutine [List] of [JokeApiProperties] which can be fetched in a Coroutine scope.
     * The @GET annotation indicates that the "realestate" endpoint will be requested with the GET
     * HTTP method
     */
    @GET("random")
    suspend fun getProperties(): List<JokeApiProperties>
}

/**
 * Main entry point for network access.
 */
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

    val jokeNetwork: JokeApiService = retrofit.create(JokeApiService::class.java)

}