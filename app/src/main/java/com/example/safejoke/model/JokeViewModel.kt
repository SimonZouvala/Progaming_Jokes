package com.example.safejoke.model

import android.app.Application
import androidx.lifecycle.*
import com.example.safejoke.database.getDatabase

import com.example.safejoke.domain.Joke
import com.example.safejoke.repository.JokeRepository
import kotlinx.coroutines.launch

class JokeViewModel(application: Application) : AndroidViewModel(application) {

    val repository = JokeRepository(getDatabase(application))

    val allJokes: LiveData<List<Joke>> = repository.allJokes

    fun insert(joke: Joke) = viewModelScope.launch {
        repository.insert(joke)
    }

    fun delete() = viewModelScope.launch {
        repository.delete()
    }

}
