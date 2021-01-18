package com.example.safejoke.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface JokeDao{

    @Query("select * from DatabaseJoke")
    fun getJokes():LiveData<List<DatabaseJoke>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertJoke (databaseJoke: DatabaseJoke)

    @Delete
    fun deleteJoke(databaseJoke: DatabaseJoke)

}

@Database(entities = [DatabaseJoke::class], version = 1)
abstract class JokeRoomDatabase: RoomDatabase() {
    abstract val videoDao: JokeDao
}

private lateinit var INSTANCE: JokeRoomDatabase

fun getDatabase(context: Context): JokeRoomDatabase {
    synchronized(JokeRoomDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                    JokeRoomDatabase::class.java,
                    "jokes").build()
        }
    }
    return INSTANCE
}