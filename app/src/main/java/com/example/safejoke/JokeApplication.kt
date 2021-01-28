package com.example.safejoke

import android.app.Application
import com.example.safejoke.database.getDatabase
import com.example.safejoke.repository.JokeRepository

/**
 * Override application to setup background work via WorkManager
 */
class JokeApplication : Application() {

    /**
     *  Using by lazy so the database and the repository are only created when they're needed
     *  rather than when the application starts
     */
    val database by lazy { getDatabase(this) }
    val repository by lazy { JokeRepository(database.jokeDatabaseDao) }
}


