package com.example.safejoke.database

import androidx.room.Entity
import com.example.safejoke.domain.Joke

/**
 * Database entity is responsible for reading and writing from the database.
 *
 */
@Entity(primaryKeys = ["setup", "punchline"])
data class DatabaseJoke constructor(
    val setup: String,
    val punchline: String
)

/**
 * Map DatabaseJoke to domain entities
 */
fun List<DatabaseJoke>.asDomainModel(): List<Joke> {
    return map {
        Joke(
            setup = it.setup,
            punchline = it.punchline
        )
    }
}