package com.example.safejoke.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Interface for Database, that responsible for operation with Database
 */
@Dao
interface JokeDatabaseDao {

    /**
     * Get all Jokes from Database
     */
    @Query("select * from DatabaseJoke")
    fun getJokes(): LiveData<List<DatabaseJoke>>

    /**
     * Insert new Joke to Database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertJoke(databaseJoke: DatabaseJoke)

    /**
     * Delete all Jokes from Database
     */
    @Query("Delete from DatabaseJoke")
    fun deleteJokes()

}