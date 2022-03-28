package io.github.xinkev.movies.database.relationships

import androidx.room.Embedded
import androidx.room.Relation
import io.github.xinkev.movies.database.entities.MovieCache
import io.github.xinkev.movies.database.entities.PopularMovieEntry


data class PopularMovieCache(
    @Embedded val entry: PopularMovieEntry,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "id"
    )
    val data: MovieCache,
)
