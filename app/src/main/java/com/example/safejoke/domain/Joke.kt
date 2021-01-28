package com.example.safejoke.domain

import com.example.safejoke.database.DatabaseJoke

/**
 * A basic class representing an entity that is used as object Joke
 */
data class Joke(
    var setup: String,
    var punchline: String
)

/**
 * Map Joke to Database entity
 */
fun Joke.asDatabaseModel(): DatabaseJoke {
    return DatabaseJoke(
        setup = this.setup,
        punchline = this.punchline
    )
}