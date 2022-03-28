package io.github.xinkev.movies.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import io.github.xinkev.movies.database.Database
import io.github.xinkev.movies.database.relationships.UpcomingMovieCache
import io.github.xinkev.movies.paging.UpcomingMoviesRemoteMediator
import io.github.xinkev.movies.remote.RetrofitClient
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class UpcomingMovieRepository @Inject constructor(
    private val db: Database,
    private val retrofitClient: RetrofitClient,
) {
    private val upcomingMovieDao = db.upcomingMovieDao()
    val upcomingMoviesFlow: Flow<PagingData<UpcomingMovieCache>>
        get() = Pager(
            config = PagingConfig(pageSize = 40),
            remoteMediator = UpcomingMoviesRemoteMediator(db = db, api = retrofitClient.movieDBApi)
        ) {
            upcomingMovieDao.getAll()
        }.flow
}