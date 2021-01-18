package com.example.safejoke.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface JokeDatabaseDao {

    @Query("select * from DatabaseJoke")
    fun getJokes(): LiveData<List<DatabaseJoke>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertJoke(databaseJoke: DatabaseJoke)

    @Query("Delete from DatabaseJoke")
    fun deleteJokes()

}