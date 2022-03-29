package io.github.xinkev.movies.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.xinkev.movies.database.Database
import io.github.xinkev.movies.utils.AppCoroutineDispatchers
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun database(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        Database::class.java,
        "movies.db"
    ).build()

    @Provides
    @Singleton
    fun ioDispatcher(): AppCoroutineDispatchers = AppCoroutineDispatchers(io = Dispatchers.IO)
}