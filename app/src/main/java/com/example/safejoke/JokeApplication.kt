package com.example.safejoke

import android.app.Application
import com.example.safejoke.database.JokeRoomDatabase
import com.example.safejoke.database.getDatabase
import com.example.safejoke.repository.JokeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class JokeApplication : Application() {



    val database by lazy { getDatabase(this) }
    val repository by lazy { JokeRepository(database) }
}


