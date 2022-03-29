package io.github.xinkev.movies.ui.screens.details.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.xinkev.movies.database.relationships.MovieDetailsWithVote
import io.github.xinkev.movies.utils.runtimeDisplayFormat

@Composable
fun MovieDetailsContent(
    modifier: Modifier=Modifier,
    movieDetails: MovieDetailsWithVote?,
    onVoteClick: (movieId: Long, voted: Boolean) -> Unit
) {
    PosterImage(movieDetails?.data?.posterPath)
    Spacer(modifier = Modifier.height(16.dp))

    Column(modifier = modifier) {
        if (movieDetails != null) {
            val data = movieDetails.data
            val voted = movieDetails.voteEntry != null
            val favoriteIcon = if (voted) Icons.Default.Favorite else Icons.Default.FavoriteBorder
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text(text = data.title, fontWeight = FontWeight.Bold)
                    Text(
                        text = "${data.country} | ${data.releaseDate}",
                        style = MaterialTheme.typography.subtitle2,
                        color = Color.Gray.copy(alpha = 0.9f)
                    )
                }
                Column {
                    Icon(
                        imageVector = favoriteIcon,
                        contentDescription = "Click to vote or remove the vote",
                        tint = Color.Red,
                        modifier = Modifier.clickable(onClick = { onVoteClick(data.id, !voted) })
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${data.voteCount}",
                        style = MaterialTheme.typography.subtitle2,
                        color = Color.Gray.copy(alpha = 0.9f)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = "${runtimeDisplayFormat(data.runtime)} | ${data.genres}",
                    color = Color(0xff6a96ef),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = data.originalLanguage.uppercase(),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Text(text = "Movie Description", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = data.overview)
        }
    }
}

