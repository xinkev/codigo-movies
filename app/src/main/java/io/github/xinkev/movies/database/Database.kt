package io.github.xinkev.movies.database

import androidx.room.Database
import androidx.room.RoomDatabase
import io.github.xinkev.movies.database.dao.*
import io.github.xinkev.movies.database.entities.MovieCache
import io.github.xinkev.movies.database.entities.PopularMovieEntry
import io.github.xinkev.movies.database.entities.RemoteKey
import io.github.xinkev.movies.database.entities.UpcomingMovieEntry

@Database(
    entities = [
        PopularMovieEntry::class,
        UpcomingMovieEntry::class,
        MovieCache::class,
        RemoteKey::class,
    ],
    version = 1
)
abstract class Database : RoomDatabase() {
    abstract fun popularMovieDao(): PopularMovieDao
    abstract fun moviesDao(): MovieDao
    abstract fun popularTableDao(): PopularTableDao
    abstract fun remoteKeyDao(): RemoteKeyDao
    abstract fun upcomingMovieDao(): UpcomingMovieDao
    abstract fun upcomingEntryDao(): UpcomingEntryDao
}