package io.github.xinkev.movies.database.dao

import androidx.paging.PagingSource
import androidx.room.*
import io.github.xinkev.movies.database.entities.Movie
import io.github.xinkev.movies.database.relationships.PopularMovie
import io.github.xinkev.movies.database.relationships.UpcomingMovie

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<Movie>)

    @Transaction
    @Query("SELECT * FROM PopularMovie")
    fun getAllPopularMovies(): PagingSource<Int, PopularMovie>

    @Transaction
    @Query(
        """
        DELETE FROM Movie 
        WHERE EXISTS (SELECT movieId 
                        FROM PopularMovie 
                        WHERE PopularMovie.movieId = Movie.id)
        """
    )
    fun deleteAllPopularMovies()

    @Transaction
    @Query(
        """
        DELETE FROM Movie 
        WHERE EXISTS (SELECT movieId 
                        FROM UpcomingMovie 
                        WHERE UpcomingMovie.movieId = Movie.id)
        """
    )
    fun deleteAllUpcomingMovies()


    @Transaction
    @Query("SELECT * FROM UpcomingMovie")
    fun getAllUpcomingMovies(): PagingSource<Int, UpcomingMovie>
}