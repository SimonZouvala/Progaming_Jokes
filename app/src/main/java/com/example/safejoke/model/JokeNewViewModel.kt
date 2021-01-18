package com.example.safejoke.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.safejoke.database.getDatabase
import com.example.safejoke.domain.Joke
import com.example.safejoke.repository.JokeRepository
import kotlinx.coroutines.launch

class JokeNewViewModel(application: Application) : AndroidViewModel(application)  {

    val repository = JokeRepository(getDatabase(application))


    suspend fun generateJoke(): Joke {
        return repository.getNewJoke()
    }
}