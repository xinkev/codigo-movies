package io.github.xinkev.movies.database.relationships

import androidx.room.Embedded
import androidx.room.Relation
import io.github.xinkev.movies.database.entities.Movie
import io.github.xinkev.movies.database.entities.MovieVoteEntry
import io.github.xinkev.movies.database.entities.UpcomingMovieEntry

data class UpcomingMovie(
    @Embedded val entry: UpcomingMovieEntry,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "id",
    )
    val data: Movie,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "movieId",
    )
    val voteEntry: MovieVoteEntry?,
)
