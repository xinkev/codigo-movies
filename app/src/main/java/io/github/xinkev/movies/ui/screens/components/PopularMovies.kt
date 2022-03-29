package io.github.xinkev.movies.ui.screens.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage
import io.github.xinkev.movies.database.relationships.PopularMovie
import io.github.xinkev.movies.ui.components.ErrorMessage
import io.github.xinkev.movies.ui.components.LoadingView
import io.github.xinkev.movies.ui.components.Votes

@OptIn(ExperimentalFoundationApi::class)
@Suppress("FunctionName")
fun LazyListScope.PopularMovies(
    popularMovies: LazyPagingItems<PopularMovie>,
    onVoteUpdate: (movieId: Long, voted: Boolean) -> Unit
) {
    item {
        Column(modifier = Modifier.height(280.dp)) {
            Text(
                text = "Popular",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp, start = 16.dp, bottom = 8.dp)
            )
            LazyRow {
                showLoadingState(
                    loadState = popularMovies.loadState.refresh,
                    onRetryClick = { popularMovies.refresh() }
                )
                itemsIndexed(popularMovies, key = { _, movie -> movie.data.id }) { index, item ->
                    item?.let { movie ->
                        if (index == 0) {
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                        ListItem(
                            movie,
                            onVoteClick = { voted -> onVoteUpdate(movie.data.id, voted) })
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
                showLoadingState(
                    loadState = popularMovies.loadState.append,
                    onRetryClick = { popularMovies.retry() },
                )
            }
        }
    }
}

@Composable
private fun ListItem(movie: PopularMovie, onVoteClick: (voted: Boolean) -> Unit) {
    Column(modifier = Modifier.width(IntrinsicSize.Min)) {
        GlideImage(
            imageModel = "https://image.tmdb.org/t/p/w500${movie.data.posterPath}",
            circularReveal = CircularReveal(),
            modifier = Modifier
                .height(150.dp)
                .aspectRatio(0.7f)
        )
        Text(
            text = movie.data.title,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        Votes(
            voteCount = movie.data.voteCount,
            voted = movie.voteEntry != null,
            onVoteClick = onVoteClick
        )
    }
}

private fun LazyListScope.showLoadingState(
    loadState: LoadState,
    onRetryClick: () -> Unit
) {
    if (loadState == LoadState.Loading) {
        item {
            LoadingView(modifier = Modifier.size(200.dp))
        }
    } else if (loadState is LoadState.Error) {
        item {
            ErrorMessage(
                modifier = Modifier
                    .size(200.dp),
                t = loadState.error,
                onRetryClick = onRetryClick
            )
        }
    }
}