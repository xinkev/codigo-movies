package io.github.xinkev.movies.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import io.github.xinkev.movies.database.entities.MovieVoteEntry

@Dao
interface MovieVoteEntryDao {
    @Insert(onConflict = IGNORE)
    suspend fun insert(entry: MovieVoteEntry)

    @Query("DELETE FROM MovieVote WHERE movieId=:movieId")
    suspend fun deleteByMovieId(movieId: Long)
}