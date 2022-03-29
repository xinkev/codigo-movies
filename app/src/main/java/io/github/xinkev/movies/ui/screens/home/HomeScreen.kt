package io.github.xinkev.movies.ui.screens.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import io.github.xinkev.movies.ui.screens.home.components.PopularMovies
import io.github.xinkev.movies.ui.screens.home.components.UpcomingMovies
import io.github.xinkev.movies.ui.screens.home.HomeViewModel

@Composable
fun HomeScreen(
    goToDetails: (movieId: Long) -> Unit,
    vm: HomeViewModel,
) {
    val popularMovies = vm.popularMovies.collectAsLazyPagingItems()
    val upcomingMovies = vm.upcomingMovies.collectAsLazyPagingItems()
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        PopularMovies(
            popularMovies = popularMovies,
            onVoteUpdate = vm::onVoteUpdate,
            onMovieClick = goToDetails
        )
        UpcomingMovies(
            upcomingMovies = upcomingMovies,
            onVoteUpdate = vm::onVoteUpdate,
            onMovieClick = goToDetails
        )
    }
}

