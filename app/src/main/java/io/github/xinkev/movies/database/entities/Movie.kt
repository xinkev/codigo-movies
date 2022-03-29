package io.github.xinkev.movies.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.xinkev.movies.remote.models.MovieResponse

@Entity(tableName = "Movie")
data class Movie(
    @PrimaryKey
    val id: Long,
    val adult: Boolean,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String?,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int,
) {
    companion object {
        fun fromResponse(response: MovieResponse): Movie =
            Movie(
                id = response.id,
                adult = response.adult,
                originalLanguage = response.originalLanguage,
                originalTitle = response.originalTitle,
                overview = response.overview,
                popularity = response.popularity,
                posterPath = response.posterPath,
                releaseDate = response.releaseDate,
                title = response.title,
                video = response.video,
                voteAverage = response.voteAverage,
                voteCount = response.voteCount,
            )
    }
}