package com.example.safejoke.repository

import android.annotation.SuppressLint
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.safejoke.database.JokeDatabaseDao
import com.example.safejoke.database.asDomainModel
import com.example.safejoke.domain.Joke
import com.example.safejoke.domain.asDatabaseModel
import com.example.safejoke.network.JokeApi
import com.example.safejoke.network.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository for fetching joke from the network and get joke from store
 */
class JokeRepository(private val jokeDatabaseDao: JokeDatabaseDao) {

    val allJokes: LiveData<List<Joke>> =
        Transformations.map(jokeDatabaseDao.getJokes()) {
            it.asDomainModel()
        }

    /**
     * Insert new Joke to Database
     * @param joke the new Joke to insert to Database
     */
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(joke: Joke) {
        withContext(Dispatchers.IO) {
            jokeDatabaseDao.insertJoke(joke.asDatabaseModel())
        }
    }

    /**
     * Delete all Jokes in Database
     */
    @SuppressLint("WrongThread")
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete() {
        withContext(Dispatchers.IO) {
            jokeDatabaseDao.deleteJokes()
        }
    }

    /**
     * Get new Joke from network.
     */
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getNewJoke(): Joke {
        var newJoke: Joke
        withContext(Dispatchers.IO) {
            newJoke = JokeApi.jokeNetwork.getProperties()[0].asDomainModel()
        }
        return newJoke
    }


}