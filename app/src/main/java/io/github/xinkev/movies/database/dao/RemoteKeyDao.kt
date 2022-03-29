package io.github.xinkev.movies.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.xinkev.movies.database.entities.RemoteKey

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKey: RemoteKey)

    @Query("SELECT * FROM RemoteKeys WHERE id=:id")
    suspend fun getById(id: String): RemoteKey?

    @Query("DELETE FROM RemoteKeys")
    suspend fun deleteAll()
}