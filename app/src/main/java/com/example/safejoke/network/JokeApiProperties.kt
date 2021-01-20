package com.example.safejoke.network

import com.example.safejoke.domain.Joke
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


data class JokeApiContainer(@Json(name = "") val jokes: List<JokeApiProperties>)


data class JokeApiProperties(
    val id: Int,
    val type: String,
    val setup: String,
    val punchline: String
)


fun JokeApiContainer.asDomainModel(): List<Joke> {
    return jokes.map {
        Joke(
            setup = it.setup,
            punchline = it.punchline
        )
    }

}

fun JokeApiProperties.asDomainModel(): Joke {
    return Joke(
        setup = this.setup,
        punchline = this.punchline
    )


}
