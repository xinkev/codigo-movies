package io.github.xinkev.movies.ui.screens.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.xinkev.movies.ui.screens.details.components.MovieDetailsContent
import io.github.xinkev.movies.ui.screens.details.components.StatusBlock
import io.github.xinkev.movies.ui.screens.details.components.Toolbar

@Composable
fun MovieDetailsScreen(movieId: Long?, vm: MovieDetailsViewModel, goBack: () -> Unit) {
    LaunchedEffect(movieId) {
        if (movieId != null) {
            vm.getMovieDetails(movieId)
        }
    }
    Box {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            MovieDetailsContent(
                modifier = Modifier
                    .padding(16.dp),
                movieDetails = vm.loadState.data,
                onVoteClick = vm::onVoteUpdate
            )
            StatusBlock( loadState = vm.loadState) {
                vm.getMovieDetails(movieId!!)
            }
        }

        Toolbar(goBack = goBack)
    }
}






