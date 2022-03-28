package io.github.xinkev.movies.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import io.github.xinkev.movies.database.entities.UpcomingMovieEntry

@Dao
interface UpcomingEntryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entries: List<UpcomingMovieEntry>)
}