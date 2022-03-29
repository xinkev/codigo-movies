package io.github.xinkev.movies.remote

import io.github.xinkev.movies.remote.models.MovieDetailsResponse
import io.github.xinkev.movies.remote.models.PopularMoviesResponse
import io.github.xinkev.movies.remote.models.UpcomingMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDBApi {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int?,
        @Query("language") language: String? = null,
        @Query("region") region: String? = null,
    ): PopularMoviesResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int?,
        @Query("language") language: String? = null,
        @Query("region") region: String? = null,
    ): UpcomingMoviesResponse

    @GET("movie/{movieId}")
    suspend fun getMovieDetails(
        @Path("movieId") movieId: Long,
        @Query("language") language: String? = null,
    ): MovieDetailsResponse
}