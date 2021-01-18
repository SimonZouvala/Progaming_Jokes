package com.example.safejoke.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.safejoke.database.DatabaseJoke
import com.example.safejoke.database.JokeDao
import com.example.safejoke.database.asDomainModel
import com.example.safejoke.domain.Joke
import com.example.safejoke.network.JokeApiService


class JokesRepository(private val jokeDao: JokeDao, private val jokeApiService: JokeApiService) {

    val allJokes: LiveData<List<Joke>> =Transformations.map(jokeDao.getJokes()){
        it.asDomainModel()}

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(databaseJoke: DatabaseJoke){
        jokeDao.insertJoke(databaseJoke)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(databaseJoke: DatabaseJoke){
        jokeDao.deleteJoke(databaseJoke)
    }




}