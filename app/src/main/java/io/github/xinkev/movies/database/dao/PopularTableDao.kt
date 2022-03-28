package io.github.xinkev.movies.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import io.github.xinkev.movies.database.entities.PopularMovieEntry

@Dao
interface PopularTableDao {
    @Query("DELETE FROM PopularMovie")
    suspend fun deleteAll()

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(entries: List<PopularMovieEntry>)
}