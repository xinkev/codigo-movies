package io.github.xinkev.movies.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.paging.RemoteMediator.MediatorResult.Error
import androidx.paging.RemoteMediator.MediatorResult.Success
import androidx.room.withTransaction
import io.github.xinkev.movies.database.Database
import io.github.xinkev.movies.database.entities.Movie
import io.github.xinkev.movies.database.entities.PopularMovieEntry
import io.github.xinkev.movies.database.entities.RemoteKey
import io.github.xinkev.movies.database.entities.RemoteKeyIds
import io.github.xinkev.movies.database.relationships.PopularMovie
import io.github.xinkev.movies.remote.MovieDBApi
import io.github.xinkev.movies.remote.models.PopularMoviesResponse
import okio.IOException
import retrofit2.HttpException

@OptIn(ExperimentalPagingApi::class)
class PopularMoviesRemoteMediator(
    private val api: MovieDBApi,
    private val db: Database,
) : RemoteMediator<Int, PopularMovie>() {
    private val popularTableDao = db.popularTableDao()
    private val movieDao = db.moviesDao()
    private val remoteKeyDao = db.remoteKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PopularMovie>
    ): MediatorResult {
        return try {
            val response = fetch(loadType) ?: return Success(endOfPaginationReached = true)
            val remoteKey = store(response, loadType)
            Success(endOfPaginationReached = remoteKey.nextPage == null)
        } catch (e: IOException) {
            Error(e)
        } catch (e: HttpException) {
            Error(e)
        }
    }

    private suspend fun fetch(loadType: LoadType): PopularMoviesResponse? {
        val loadKey = when (loadType) {
            LoadType.REFRESH -> null
            LoadType.PREPEND -> return null
            LoadType.APPEND -> {
                val remoteKey = db.withTransaction {
                    remoteKeyDao.getById(RemoteKeyIds.upcomingMovies)
                }
                if (remoteKey?.nextPage == null) {
                    return null
                }
                remoteKey.nextPage
            }
        }

        return api.getPopularMovies(page = loadKey)
    }

    private suspend fun store(
        response: PopularMoviesResponse,
        loadType: LoadType
    ): RemoteKey {
        val remoteKey = RemoteKey(
            id = RemoteKeyIds.popularMovies,
            currentPage = response.page,
            nextPage = if (response.page < response.totalPages) response.page + 1 else null,
            prevPage = if (response.page > 1) response.page - 1 else null
        )
        db.withTransaction {
            if (loadType == LoadType.REFRESH) {
                movieDao.deleteAllPopularMovies()
            }
            remoteKeyDao.insertOrReplace(remoteKey)
            val movies = response.results.map { Movie.fromResponse(it) }
            val popularTableEntries = movies.map { PopularMovieEntry(movieId = it.id) }
            movieDao.insertAll(movies)
            popularTableDao.insertAll(popularTableEntries)
        }
        return remoteKey
    }
}