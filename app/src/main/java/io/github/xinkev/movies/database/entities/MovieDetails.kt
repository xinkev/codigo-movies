package io.github.xinkev.movies.database.entities

import androidx.room.*
import io.github.xinkev.movies.remote.models.MovieDetailsResponse

@Entity(
    tableName = "MovieDetails",
    indices = [Index(value = ["id"], unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = Movie::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("id"),
            onUpdate = ForeignKey.NO_ACTION,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MovieDetails(
    @PrimaryKey
    val id: Long,
    val adult: Boolean,
    val budget: Int,
    val genres: String,
    val backdropPath: String?,
    val imdbId: String,
    val homepage: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String?,
    val originalLanguage: String,
    val originalTitle: String,
    val releaseDate: String,
    val revenue: Int,
    val country: String,
    val runtime: Int,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
) {
    companion object {
        fun fromResponse(response: MovieDetailsResponse) = MovieDetails(
            id = response.id,
            adult = response.adult,
            budget = response.budget,
            imdbId = response.imdbId,
            homepage = response.homepage,
            overview = response.overview,
            popularity = response.popularity,
            posterPath = response.posterPath,
            genres = response.genres.joinToString(", ") { it.name },
            releaseDate = response.releaseDate,
            revenue = response.revenue,
            runtime = response.runtime,
            status = response.status,
            tagline = response.tagline,
            title = response.title,
            video = response.video,
            voteCount = response.voteCount,
            voteAverage = response.voteAverage,
            originalTitle = response.originalTitle,
            originalLanguage = response.originalLanguage,
            backdropPath = response.backdropPath,
            country = response.productionCountries.first().iso31661
        )
    }
}
