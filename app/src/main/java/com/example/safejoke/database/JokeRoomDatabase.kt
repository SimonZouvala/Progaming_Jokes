package com.example.safejoke.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * This is the backend. The database.
 */
@Database(entities = [DatabaseJoke::class], version = 1, exportSchema = false)
abstract class JokeRoomDatabase : RoomDatabase() {

    abstract val jokeDatabaseDao: JokeDatabaseDao
}

private lateinit var INSTANCE: JokeRoomDatabase

/**
 * Function to get Database
 */
fun getDatabase(context: Context): JokeRoomDatabase {

    synchronized(JokeRoomDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                JokeRoomDatabase::class.java,
                "jokes"
            ).build()
        }
    }

    return INSTANCE
}


