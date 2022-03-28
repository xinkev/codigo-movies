package io.github.xinkev.movies.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ErrorMessage(modifier: Modifier = Modifier, t: Throwable, onRetryClick: () -> Unit) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Column {
            Text(text = t.localizedMessage ?: "An error occurred.")
            Button(
                onClick = onRetryClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center)
            ) {
                Text(text = "Retry")
            }
        }
    }
}