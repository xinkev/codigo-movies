package io.github.xinkev.movies.ui.screens.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage
import io.github.xinkev.movies.database.relationships.PopularMovieCache
import io.github.xinkev.movies.ui.components.ErrorMessage
import io.github.xinkev.movies.ui.components.LoadingView

@Composable
fun PopularMovies(popularMovies: LazyPagingItems<PopularMovieCache>) {
    Column {
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
            itemsIndexed(popularMovies) { index, item ->
                item?.let { movie ->
                    if (index == 0) {
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Poster(movie)
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

@Composable
private fun Poster(movie: PopularMovieCache) {
    Column(modifier = Modifier.width(IntrinsicSize.Min)) {
        GlideImage(
            imageModel = "https://image.tmdb.org/t/p/w500${movie.data.posterPath}",
            circularReveal = CircularReveal(),
            modifier = Modifier
                .height(200.dp)
                .aspectRatio(0.7f)
        )
        Text(
            text = movie.data.title,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
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