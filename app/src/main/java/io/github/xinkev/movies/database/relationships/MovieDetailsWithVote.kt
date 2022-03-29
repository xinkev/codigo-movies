package io.github.xinkev.movies.database.relationships

import androidx.room.Embedded
import androidx.room.Relation
import io.github.xinkev.movies.database.entities.MovieDetails
import io.github.xinkev.movies.database.entities.MovieVoteEntry

data class MovieDetailsWithVote(
    @Embedded val data: MovieDetails,
    @Relation(
        parentColumn = "id",
        entityColumn = "movieId",
    )
    val voteEntry: MovieVoteEntry?,
)
