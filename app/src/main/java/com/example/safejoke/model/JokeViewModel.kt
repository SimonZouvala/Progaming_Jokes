package com.example.safejoke.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.safejoke.domain.Joke
import com.example.safejoke.repository.JokeRepository
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * View Model to keep a reference to the joke repository and
 * an up-to-date list of all words.
 * Also support loading and save new Joke
 *
 */
class JokeViewModel(private val repository: JokeRepository) : ViewModel() {
    /**
     * Event triggered for network error. This is private to avoid exposing a
     * way to set this value to observers.
     */
    private var _eventNetworkError = MutableLiveData(false)

    /**
     * Event triggered for network error. Views should use this to get access
     * to the data.
     */
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    val allJokes: LiveData<List<Joke>> = repository.allJokes

    /**
     * Function to insert Joke to database
     */
    fun insert(joke: Joke) = viewModelScope.launch {
        repository.insert(joke)
    }

    /**
     * Function delete all Jokes from database
     */
    fun delete() = viewModelScope.launch {
        repository.delete()
    }

    /**
     * Function generate new Joke from network
     */
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
