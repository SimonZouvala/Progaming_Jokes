package com.example.safejoke.repository

import android.annotation.SuppressLint
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.safejoke.database.DatabaseJoke
import com.example.safejoke.database.JokeRoomDatabase
import com.example.safejoke.database.asDomainModel
import com.example.safejoke.domain.Joke
import com.example.safejoke.domain.asDatabaseModel
import com.example.safejoke.network.JokeApi
import com.example.safejoke.network.asDomainModel


class JokeRepository(private val jokeRoomDatabase: JokeRoomDatabase) {

    val allJokes: LiveData<List<Joke>> =
        Transformations.map(jokeRoomDatabase.jokeDatabaseDao.getJokes()) {
            it.asDomainModel()
        }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun insert(joke: Joke) {
        jokeRoomDatabase.jokeDatabaseDao.insertJoke(DatabaseJoke(joke.setup, joke.punchline))
    }

    @SuppressLint("WrongThread")
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun delete() {
        jokeRoomDatabase.jokeDatabaseDao.deleteJokes()
    }
    @WorkerThread
    suspend fun getNewJoke(): Joke {
        return JokeApi.jokeNetwork.getProperties().asDomainModel()
    }


}