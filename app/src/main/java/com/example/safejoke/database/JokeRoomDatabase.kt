package com.example.safejoke.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope

@Database(entities = [DatabaseJoke::class], version = 1, exportSchema = false)
abstract class JokeRoomDatabase : RoomDatabase() {

    abstract val jokeDatabaseDao: JokeDatabaseDao
}

private lateinit var INSTANCE: JokeRoomDatabase

fun getDatabase(context: Context): JokeRoomDatabase {

    synchronized(JokeRoomDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                JokeRoomDatabase::class.java,
                "videos").build()
        }
    }
    return INSTANCE
}


