package io.github.xinkev.movies.ui.screens.details

import io.github.xinkev.movies.database.relationships.MovieDetailsWithVote

data class MovieDetailsLoadState(
    val isLoading: Boolean = false,
    val data: MovieDetailsWithVote? = null,
    val error: Throwable? = null,
)