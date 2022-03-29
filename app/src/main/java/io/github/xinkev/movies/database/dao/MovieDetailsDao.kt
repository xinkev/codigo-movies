package io.github.xinkev.movies.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.xinkev.movies.database.entities.MovieDetails
import io.github.xinkev.movies.database.relationships.MovieDetailsWithVote
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDetailsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: MovieDetails)

    @Query("SELECT * FROM MovieDetails WHERE id=:movieId")
    fun getMovieDetails(movieId: Long): Flow<MovieDetailsWithVote?>
}