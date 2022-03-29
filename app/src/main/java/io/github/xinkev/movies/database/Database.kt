package io.github.xinkev.movies.database

import androidx.room.Database
import androidx.room.RoomDatabase
import io.github.xinkev.movies.database.dao.*
import io.github.xinkev.movies.database.entities.*

@Database(
    entities = [
        PopularMovieEntry::class,
        UpcomingMovieEntry::class,
        Movie::class,
        RemoteKey::class,
        MovieVoteEntry::class,
        MovieDetails::class,
    ],
    version = 1
)
abstract class Database : RoomDatabase() {
    abstract fun moviesDao(): MovieDao
    abstract fun movieDetailsDao(): MovieDetailsDao
    abstract fun remoteKeyDao(): RemoteKeyDao
    abstract fun popularTableDao(): PopularTableDao
    abstract fun upcomingEntryDao(): UpcomingEntryDao
    abstract fun voteEntryDao(): MovieVoteEntryDao
}