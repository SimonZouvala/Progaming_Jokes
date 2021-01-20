package com.example.safejoke.domain

import androidx.room.PrimaryKey
import com.example.safejoke.database.DatabaseJoke

data class Joke(
    val setup: String,
    val punchline: String
)


fun List<Joke>.asDatabaseModel(): List<DatabaseJoke> {
    return map {
        DatabaseJoke(
            setup = it.setup,
            punchline = it.punchline
        )
    }
}

fun Joke.asDatabaseModel(): DatabaseJoke {
    return DatabaseJoke(
        setup = this.setup,
        punchline = this.punchline
    )

}