package io.github.xinkev.movies.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import io.github.xinkev.movies.database.relationships.UpcomingMovieCache

@Dao
interface UpcomingMovieDao {
    @Transaction
    @Query("SELECT * FROM UpcomingMovie")
    fun getAll(): PagingSource<Int, UpcomingMovieCache>

    @Transaction
    @Query(
        """
        DELETE FROM Movies 
        WHERE EXISTS (SELECT movieId 
                        FROM UpcomingMovie 
                        WHERE UpcomingMovie.movieId = Movies.id)
        """
    )
    fun deleteAll()
}