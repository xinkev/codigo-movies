package io.github.xinkev.movies.database.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "MovieVote",
    indices = [
        Index(value = ["movieId"], unique = true)
    ],
)
data class MovieVoteEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val movieId: Long,
)
