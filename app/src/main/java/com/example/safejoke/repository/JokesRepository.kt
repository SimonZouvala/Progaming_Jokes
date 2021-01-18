package com.example.safejoke.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.safejoke.database.DatabaseJoke
import com.example.safejoke.database.JokeDao
import com.example.safejoke.database.asDomainModel
import com.example.safejoke.domain.Joke
import com.example.safejoke.network.JokeApi
import com.example.safejoke.network.asDomainModel


class JokesRepository(private val jokeDao: JokeDao) {

    val allJokes: LiveData<List<Joke>> =Transformations.map(jokeDao.getJokes()){
        it.asDomainModel()}

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(joke: Joke){
        jokeDao.insertJoke(DatabaseJoke(joke.id,joke.setup,joke.punchline))
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(joke: Joke){
        jokeDao.deleteJoke(DatabaseJoke(joke.id,joke.setup,joke.punchline))
    }

    suspend fun getNewJoke(): Joke{
        return JokeApi.jokeNetwork.getProperties().asDomainModel()
    }




}