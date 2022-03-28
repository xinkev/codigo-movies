package io.github.xinkev.movies.ui.screens.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage
import io.github.xinkev.movies.database.relationships.UpcomingMovieCache
import io.github.xinkev.movies.ui.components.ErrorMessage
import io.github.xinkev.movies.ui.components.LoadingView
import io.github.xinkev.movies.ui.components.Votes

@Composable
fun UpcomingMovies(upcomingMovies: LazyPagingItems<UpcomingMovieCache>) {
    Column {
        Text(
            text = "Upcoming Movies",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp, start = 16.dp, bottom = 8.dp)
        )
        LazyColumn {
            showLoadingState(
                loadState = upcomingMovies.loadState.refresh,
                onRetryClick = { upcomingMovies.refresh() }
            )
            items(upcomingMovies, key = { it.entry.id }) { item ->
                item?.let { movie ->
                    UpcomingMovieListItem(movie)
                }
            }
            showLoadingState(
                loadState = upcomingMovies.loadState.append,
                onRetryClick = { upcomingMovies.retry() },
            )
        }
    }
}

@Composable
private fun UpcomingMovieListItem(movie: UpcomingMovieCache) {
    Row(modifier = Modifier.padding(16.dp)) {
        GlideImage(
            imageModel = "https://image.tmdb.org/t/p/w500${movie.data.posterPath}",
            circularReveal = CircularReveal(),
            modifier = Modifier
                .height(150.dp)
                .aspectRatio(0.7f)
                .padding(top = 4.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = movie.data.title, fontWeight = FontWeight.Black)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = movie.data.overview,
                style = MaterialTheme.typography.caption
            )
            Spacer(modifier = Modifier.height(8.dp))
            Votes(movie.data.voteCount)
        }
    }
}



private fun LazyListScope.showLoadingState(
    loadState: LoadState,
    onRetryClick: () -> Unit
) {
    if (loadState == LoadState.Loading) {
        item {
            LoadingView()
        }
    } else if (loadState is LoadState.Error) {
        item {
            ErrorMessage(t = loadState.error, onRetryClick = onRetryClick)
        }
    }
}