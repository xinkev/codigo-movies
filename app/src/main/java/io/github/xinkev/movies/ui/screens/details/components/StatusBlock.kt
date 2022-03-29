package io.github.xinkev.movies.ui.screens.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.xinkev.movies.ui.components.ErrorMessage
import io.github.xinkev.movies.ui.components.LoadingView
import io.github.xinkev.movies.ui.screens.details.MovieDetailsLoadState

@Composable
fun StatusBlock(
    modifier: Modifier = Modifier,
    loadState: MovieDetailsLoadState,
    onRetryClick: () -> Unit
) {
    if (!loadState.isLoading && loadState.error == null) return
    Box(
        modifier = Modifier
            .background(Color(0xFFE4E4E4))
            .fillMaxWidth()
            .height(160.dp)
            .padding(vertical = 32.dp, horizontal = 16.dp)
            .then(modifier)
    ) {
        if (loadState.isLoading) LoadingView()
        else {
            loadState.error?.let { error ->
                ErrorMessage(
                    t = error,
                    onRetryClick = onRetryClick,
                )
            }
        }
    }
}