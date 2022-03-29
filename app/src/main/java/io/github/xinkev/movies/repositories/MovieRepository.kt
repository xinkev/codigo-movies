package io.github.xinkev.movies.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import io.github.xinkev.movies.database.Database
import io.github.xinkev.movies.database.entities.MovieDetails
import io.github.xinkev.movies.database.entities.MovieVoteEntry
import io.github.xinkev.movies.database.relationships.PopularMovie
import io.github.xinkev.movies.database.relationships.UpcomingMovie
import io.github.xinkev.movies.paging.PopularMoviesRemoteMediator
import io.github.xinkev.movies.paging.UpcomingMoviesRemoteMediator
import io.github.xinkev.movies.remote.RetrofitClient
import io.github.xinkev.movies.ui.screens.details.MovieDetailsLoadState
import io.github.xinkev.movies.utils.AppCoroutineDispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class MovieRepository @Inject constructor(
    retrofitClient: RetrofitClient,
    private val db: Database,
    private val dispatchers: AppCoroutineDispatchers,
) {
    private val api = retrofitClient.movieDBApi
    private val movieDao = db.moviesDao()
    private val movieDetailsDao = db.movieDetailsDao()
    private val voteDao = db.voteEntryDao()

    val popularMoviesFlow
        get(): Flow<PagingData<PopularMovie>> = Pager(
            config = PagingConfig(pageSize = 40),
            remoteMediator = PopularMoviesRemoteMediator(api = api, db = db)
        ) {
            movieDao.getAllPopularMovies()
        }.flow

    val upcomingMoviesFlow: Flow<PagingData<UpcomingMovie>>
        get() = Pager(
            config = PagingConfig(pageSize = 40),
            remoteMediator = UpcomingMoviesRemoteMediator(db = db, api = api)
        ) {
            movieDao.getAllUpcomingMovies()
        }.flow

    suspend fun saveVoteState(movieId: Long, voted: Boolean) {
        withContext(dispatchers.io) {
            if (voted) {
                voteDao.insert(MovieVoteEntry(movieId = movieId))
            } else {
                voteDao.deleteByMovieId(movieId)
            }
        }
    }

    fun getMovieDetails(movieId: Long): Flow<MovieDetailsLoadState> {
        val cacheFlow = movieDetailsDao.getMovieDetails(movieId)
        return flow {
            emit(MovieDetailsLoadState(isLoading = true, data = cacheFlow.first()))
            val response = api.getMovieDetails(movieId)
            movieDetailsDao.insert(MovieDetails.fromResponse(response))
            emitAll(
                cacheFlow.map { MovieDetailsLoadState(isLoading = false, data = it) }
            )
        }.catch { e ->
            val error = when (e) {
                is IOException -> e
                is HttpException -> e
                else -> throw e
            }
            emitAll(cacheFlow.map {
                MovieDetailsLoadState(
                    isLoading = false,
                    data = it,
                    error = error
                )
            })
        }.flowOn(dispatchers.io)
    }
}