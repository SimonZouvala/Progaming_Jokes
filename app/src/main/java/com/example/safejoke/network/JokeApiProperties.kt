package com.example.safejoke.network

import com.example.safejoke.database.DatabaseJoke
import com.example.safejoke.domain.Joke

data class JokeApiProperties(
    val id: Int,
    val type: String,
    val setup: String,
    val punchline: String
)


fun JokeApiProperties.asDomainModel(): Joke {
    return Joke(
        id = this.id,
        setup = this.setup,
        punchline = this.punchline
    )

}