package io.github.xinkev.movies.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import io.github.xinkev.movies.database.Database
import io.github.xinkev.movies.database.entities.MovieVoteEntry
import io.github.xinkev.movies.database.relationships.PopularMovie
import io.github.xinkev.movies.database.relationships.UpcomingMovie
import io.github.xinkev.movies.paging.PopularMoviesRemoteMediator
import io.github.xinkev.movies.paging.UpcomingMoviesRemoteMediator
import io.github.xinkev.movies.remote.RetrofitClient
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class MovieRepository @Inject constructor(
    private val retrofitClient: RetrofitClient,
    private val db: Database,
) {
    private val dao = db.moviesDao()
    private val voteDao = db.voteEntryDao()

    val popularMoviesFlow
        get(): Flow<PagingData<PopularMovie>> = Pager(
            config = PagingConfig(pageSize = 40),
            remoteMediator = PopularMoviesRemoteMediator(api = retrofitClient.movieDBApi, db = db)
        ) {
            dao.getAllPopularMovies()
        }.flow

    val upcomingMoviesFlow: Flow<PagingData<UpcomingMovie>>
        get() = Pager(
            config = PagingConfig(pageSize = 40),
            remoteMediator = UpcomingMoviesRemoteMediator(db = db, api = retrofitClient.movieDBApi)
        ) {
            dao.getAllUpcomingMovies()
        }.flow

    suspend fun saveVoteState(movieId: Long, voted: Boolean) {
        if (voted) {
            voteDao.insert(MovieVoteEntry(movieId = movieId))
        } else {
            voteDao.deleteByMovieId(movieId)
        }
    }
}