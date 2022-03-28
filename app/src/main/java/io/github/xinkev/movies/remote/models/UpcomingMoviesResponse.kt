package io.github.xinkev.movies.remote.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpcomingMoviesResponse(
    val dates: Dates,
    val page: Int,
    val results: List<MovieResponse>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
) {
    @Serializable
    data class Dates(
        val maximum: String,
        val minimum: String
    )
}