package io.github.xinkev.movies.ui.screens.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage
import io.github.xinkev.movies.database.relationships.UpcomingMovie
import io.github.xinkev.movies.ui.components.ErrorMessage
import io.github.xinkev.movies.ui.components.LoadingView
import io.github.xinkev.movies.ui.components.Votes
import io.github.xinkev.movies.utils.imageUrl

@Suppress("FunctionName")
fun LazyListScope.UpcomingMovies(
    upcomingMovies: LazyPagingItems<UpcomingMovie>,
    onVoteUpdate: (movieId: Long, voted: Boolean) -> Unit,
    onMovieClick: (movieId: Long) -> Unit,
) {
    item {
        Text(
            text = "Upcoming Movies",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp, start = 16.dp, bottom = 8.dp)
        )
    }
    showLoadingState(
        loadState = upcomingMovies.loadState.refresh,
        onRetryClick = { upcomingMovies.refresh() }
    )
    items(upcomingMovies, key = { it.data.id }) { item ->
        item?.let { movie ->
            UpcomingMovieListItem(
                movie,
                onVoteClick = { voted -> onVoteUpdate(movie.data.id, voted) },
                onClick = { onMovieClick(movie.data.id) }
            )
        }
    }
    showLoadingState(
        loadState = upcomingMovies.loadState.append,
        onRetryClick = { upcomingMovies.retry() },
    )
}

@Composable
private fun UpcomingMovieListItem(
    movie: UpcomingMovie,
    onVoteClick: (voted: Boolean) -> Unit,
    onClick: () -> Unit
) {
    Row(modifier = Modifier
        .padding(16.dp)
        .clickable(onClick = onClick)) {
        GlideImage(
            imageModel = imageUrl(movie.data.posterPath),
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
                style = MaterialTheme.typography.caption,
                overflow = TextOverflow.Ellipsis,
                maxLines = 6
            )
            Spacer(modifier = Modifier.height(8.dp))
            Votes(movie.data.voteCount, movie.voteEntry != null, onVoteClick)
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