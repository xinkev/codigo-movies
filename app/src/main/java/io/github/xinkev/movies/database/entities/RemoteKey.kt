package io.github.xinkev.movies.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "RemoteKeys")
data class RemoteKey(
    @PrimaryKey
    var id: Long,
    val currentPage: Int,
    val prevPage: Int?,
    val nextPage: Int?
)