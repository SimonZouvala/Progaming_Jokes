package com.example.safejoke.database

import androidx.lifecycle.Transformations.map
import androidx.room.Entity
import com.example.safejoke.domain.Joke

@Entity
data class DatabaseJoke constructor(
        val id: Int,
        val setup: String,
        val punchline: String)


/**
 * Map DatabaseVideos to domain entities
 */
fun List<DatabaseJoke>.asDomainModel(): List<Joke> {
        return map {
                Joke(
                        id = it.id,
                        setup = it.setup,
                        punchline = it.punchline
                )
        }
}

fun DatabaseJoke.asDomainModel(): Joke {
        return Joke(
                id = this.id,
                setup = this.setup,
                punchline = this.punchline)

}