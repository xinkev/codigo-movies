package io.github.xinkev.movies.database

import androidx.room.Database
import androidx.room.RoomDatabase
import io.github.xinkev.movies.database.dao.MovieDao
import io.github.xinkev.movies.database.dao.PopularMovieDao
import io.github.xinkev.movies.database.dao.PopularTableDao
import io.github.xinkev.movies.database.dao.RemoteKeyDao
import io.github.xinkev.movies.database.entities.MovieCache
import io.github.xinkev.movies.database.entities.PopularMovieEntry
import io.github.xinkev.movies.database.entities.RemoteKey

@Database(
    entities = [
        PopularMovieEntry::class,
        MovieCache::class,
        RemoteKey::class
    ],
    version = 1
)
abstract class Database : RoomDatabase() {
    abstract fun popularMovieDao(): PopularMovieDao
    abstract fun moviesDao(): MovieDao
    abstract fun popularTableDao(): PopularTableDao
    abstract fun remoteKeyDao(): RemoteKeyDao
}