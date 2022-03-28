package io.github.xinkev.movies.remote

import io.github.xinkev.movies.remote.models.PopularMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDBApi {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int?,
        @Query("language") language: String? = null,
        @Query("region") region: String? = null,
    ): PopularMoviesResponse
}