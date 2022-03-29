package io.github.xinkev.movies.remote.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsResponse(
    val adult: Boolean,
    @SerialName("backdrop_path")
    val backdropPath: String?,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String,
    val id: Long,
    @SerialName("imdb_id")
    val imdbId: String,
    @SerialName("original_language")
    val originalLanguage: String,
    @SerialName("original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("production_countries")
    val productionCountries: List<ProductionCountry>,
    @SerialName("release_date")
    val releaseDate: String,
    val revenue: Int,
    val runtime: Int,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Int
) {
    @Serializable
    data class Genre(
        val id: Long,
        val name: String
    )

    @Serializable
    data class ProductionCountry(
        @SerialName("iso_3166_1")
        val iso31661: String,
        val name: String
    )
}