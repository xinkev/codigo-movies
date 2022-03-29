package io.github.xinkev.movies.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import io.github.xinkev.movies.ui.screens.components.PopularMovies
import io.github.xinkev.movies.ui.screens.components.UpcomingMovies

@Composable
fun HomeScreen(vm: HomeViewModel = hiltViewModel()) {
    Column(modifier = Modifier.fillMaxSize()) {
        PopularMovies(vm.popularMovies.collectAsLazyPagingItems(), onVoteUpdate = vm::onVoteUpdate)
        UpcomingMovies(
            vm.upcomingMovies.collectAsLazyPagingItems(),
            onVoteUpdate = vm::onVoteUpdate
        )
    }
}

