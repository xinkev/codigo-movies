package io.github.xinkev.movies.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import io.github.xinkev.movies.ui.screens.components.PopularMovies
import io.github.xinkev.movies.ui.screens.components.UpcomingMovies

@Composable
fun HomeScreen(vm: HomeViewModel = hiltViewModel()) {
    val popularMovies = vm.popularMovies.collectAsLazyPagingItems()
    val upcomingMovies = vm.upcomingMovies.collectAsLazyPagingItems()
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        PopularMovies(
            popularMovies = popularMovies,
            onVoteUpdate = vm::onVoteUpdate
        )
        UpcomingMovies(
            upcomingMovies = upcomingMovies,
            onVoteUpdate = vm::onVoteUpdate
        )
    }
}

