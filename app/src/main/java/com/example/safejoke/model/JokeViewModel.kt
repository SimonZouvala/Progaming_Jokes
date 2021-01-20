package com.example.safejoke.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.safejoke.domain.Joke
import com.example.safejoke.repository.JokeRepository
import kotlinx.coroutines.launch

class JokeViewModel(private val repository: JokeRepository) : ViewModel() {


    val allJokes: LiveData<List<Joke>> = repository.allJokes

    fun insert(joke: Joke) = viewModelScope.launch {
        repository.insert(joke)
    }

    fun delete() = viewModelScope.launch {
        repository.delete()
    }

    suspend fun generateJoke(): Joke {
        var newJoke: Joke =  Joke("","")
        viewModelScope.launch {
            newJoke = repository.getNewJoke()
        }
        return newJoke
    }

}
