package io.github.xinkev.movies.ui.screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import io.github.xinkev.movies.ui.screens.components.PopularMovies

@Composable
fun HomeScreen(vm: HomeViewModel = hiltViewModel()) {
    PopularMovies(vm.popularMovies.collectAsLazyPagingItems())
}

