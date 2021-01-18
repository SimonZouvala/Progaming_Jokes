package com.example.safejoke.database

import androidx.lifecycle.Transformations.map
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.safejoke.domain.Joke

@Entity(primaryKeys = arrayOf("setup", "punchline"))
data class DatabaseJoke constructor(
    val setup: String,
    val punchline: String){

}


/**
 * Map DatabaseVideos to domain entities
 */
fun List<DatabaseJoke>.asDomainModel(): List<Joke> {
    return map {
        Joke(
            setup = it.setup,
            punchline = it.punchline
        )
    }
}

fun DatabaseJoke.asDomainModel(): Joke {
    return Joke(
        setup = this.setup,
        punchline = this.punchline)

}