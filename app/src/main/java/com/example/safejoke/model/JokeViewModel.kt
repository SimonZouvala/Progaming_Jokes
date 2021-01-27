package com.example.safejoke.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.safejoke.domain.Joke
import com.example.safejoke.repository.JokeRepository
import kotlinx.coroutines.launch
import java.io.IOException

class JokeViewModel(private val repository: JokeRepository) : ViewModel() {
    /**
     * Event triggered for network error. This is private to avoid exposing a
     * way to set this value to observers.
     */
    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    /**
     * Event triggered for network error. Views should use this to get access
     * to the data.
     */
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private val jokeValues = MutableLiveData<Joke>()

    fun getJoke(): LiveData<Joke> {
        return jokeValues
    }

    fun setText(setup: String, punchline: String) {
        jokeValues.value = Joke(setup, punchline)
    }

    val allJokes: LiveData<List<Joke>> = repository.allJokes

    fun insert(joke: Joke) = viewModelScope.launch {
        repository.insert(joke)
    }

    fun delete() = viewModelScope.launch {
        repository.delete()
    }

    suspend fun generateJoke(): Joke {
        var newJoke = Joke("", "")
        try {
            newJoke = repository.getNewJoke()
            _eventNetworkError.value = false

        } catch (networkError: IOException) {
            _eventNetworkError.value = true
        }

        return newJoke
    }



}
