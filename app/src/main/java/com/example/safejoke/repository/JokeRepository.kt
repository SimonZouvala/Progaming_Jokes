package com.example.safejoke.repository

import android.annotation.SuppressLint
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.safejoke.database.DatabaseJoke
import com.example.safejoke.database.JokeDatabaseDao
import com.example.safejoke.database.JokeRoomDatabase
import com.example.safejoke.database.asDomainModel
import com.example.safejoke.domain.Joke
import com.example.safejoke.domain.asDatabaseModel
import com.example.safejoke.network.JokeApi
import com.example.safejoke.network.JokeApiProperties
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

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(joke: Joke) {
        withContext(Dispatchers.IO) {
            jokeDatabaseDao.insertJoke(joke.asDatabaseModel())
        }
    }

    @SuppressLint("WrongThread")
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete() {
        withContext(Dispatchers.IO) {
            jokeDatabaseDao.deleteJokes()
        }
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getNewJoke(): Joke {
        var newJoke: JokeApiProperties
        withContext(Dispatchers.IO){
            newJoke = JokeApi.jokeNetwork.getProperties().get(0)
        }
        return Joke(newJoke.setup,newJoke.punchline)
    }



}