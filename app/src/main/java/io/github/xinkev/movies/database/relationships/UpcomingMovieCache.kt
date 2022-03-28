package io.github.xinkev.movies.database.relationships

import androidx.room.Embedded
import androidx.room.Relation
import io.github.xinkev.movies.database.entities.MovieCache
import io.github.xinkev.movies.database.entities.UpcomingMovieEntry

data class UpcomingMovieCache(
    @Embedded val entry: UpcomingMovieEntry,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "id",
    )
    val data: MovieCache,
)
