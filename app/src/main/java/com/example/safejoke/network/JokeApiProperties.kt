package com.example.safejoke.network

import com.example.safejoke.domain.Joke

/**
 * This data class defines a JokeAPI property which includes an ID,
 * the type, the setup and the punchline
 */
data class JokeApiProperties(
    val id: Int,
    val type: String,
    val setup: String,
    val punchline: String
)

/**
 * Map JokeApiProperties to domain entity
 */
fun JokeApiProperties.asDomainModel(): Joke {
    return Joke(
        setup = this.setup,
        punchline = this.punchline
    )
}
