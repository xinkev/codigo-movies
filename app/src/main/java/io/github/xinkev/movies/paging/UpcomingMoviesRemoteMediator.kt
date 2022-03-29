package io.github.xinkev.movies.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import io.github.xinkev.movies.database.Database
import io.github.xinkev.movies.database.entities.Movie
import io.github.xinkev.movies.database.entities.RemoteKey
import io.github.xinkev.movies.database.entities.RemoteKeyIds
import io.github.xinkev.movies.database.entities.UpcomingMovieEntry
import io.github.xinkev.movies.database.relationships.UpcomingMovie
import io.github.xinkev.movies.remote.MovieDBApi
import io.github.xinkev.movies.remote.models.UpcomingMoviesResponse
import okio.IOException
import retrofit2.HttpException

@OptIn(ExperimentalPagingApi::class)
class UpcomingMoviesRemoteMediator constructor(
    private val db: Database,
    private val api: MovieDBApi,
) : RemoteMediator<Int, UpcomingMovie>() {
    private val remoteKeyDao = db.remoteKeyDao()
    private val movieDao = db.moviesDao()
    private val upcomingEntryDao = db.upcomingEntryDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UpcomingMovie>
    ): MediatorResult {
        return try {
            val response =
                fetch(loadType) ?: return MediatorResult.Success(endOfPaginationReached = true)
            val remoteKey = store(response, loadType)
            MediatorResult.Success(endOfPaginationReached = remoteKey.nextPage == null)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun fetch(loadType: LoadType): UpcomingMoviesResponse? {
        val nextPage = when (loadType) {
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
        return api.getUpcomingMovies(page = nextPage)
    }

    private suspend fun store(response: UpcomingMoviesResponse, loadType: LoadType): RemoteKey {
        val remoteKey = RemoteKey(
            id = RemoteKeyIds.upcomingMovies,
            currentPage = response.page,
            nextPage = if (response.page < response.totalPages) response.page + 1 else null,
            prevPage = if (response.page > 1) response.page - 1 else null
        )
        db.withTransaction {
            if (loadType == LoadType.REFRESH) {
                movieDao.deleteAllUpcomingMovies()
            }
            remoteKeyDao.insertOrReplace(remoteKey)
            val movies = response.results.map { Movie.fromResponse(it) }
            val upcomingEntries = movies.map { movie -> UpcomingMovieEntry(movieId = movie.id) }
            movieDao.insertAll(movies)
            upcomingEntryDao.insertAll(upcomingEntries)
        }
        return remoteKey
    }
}