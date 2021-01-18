package com.example.safejoke.network

import com.example.safejoke.domain.Joke
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class JokeApiProperties(
    val id: Int,
    val type: String,
    val setup: String,
    val punchline: String
)


fun JokeApiProperties.asDomainModel(): Joke {
    return Joke(
        setup = this.setup,
        punchline = this.punchline
    )

}
