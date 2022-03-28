package io.github.xinkev.movies.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import io.github.xinkev.movies.database.relationships.PopularMovieCache

@Dao
interface PopularMovieDao {
    @Query(
        """
        SELECT PopularMovie.* FROM PopularMovie
         JOIN Movies ON Movies.id = PopularMovie.movieId
        ORDER BY popularity DESC"""
    )
    fun getAll(): PagingSource<Int, PopularMovieCache>
}