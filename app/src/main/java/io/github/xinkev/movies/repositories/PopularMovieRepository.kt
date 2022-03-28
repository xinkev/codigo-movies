package io.github.xinkev.movies.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import io.github.xinkev.movies.database.Database
import io.github.xinkev.movies.database.relationships.PopularMovieCache
import io.github.xinkev.movies.paging.PopularMoviesRemoteMediator
import io.github.xinkev.movies.remote.RetrofitClient
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class PopularMovieRepository @Inject constructor(
    private val retrofitClient: RetrofitClient,
    private val db: Database,
) {
    private val dao = db.popularMovieDao()
    val popularMoviesFlow
        get(): Flow<PagingData<PopularMovieCache>> = Pager(
            config = PagingConfig(pageSize = 40),
            remoteMediator = PopularMoviesRemoteMediator(retrofitClient = retrofitClient, db = db)
        ) {
            dao.getAll()
        }.flow
}