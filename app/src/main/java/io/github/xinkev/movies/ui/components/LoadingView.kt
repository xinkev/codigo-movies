package io.github.xinkev.movies.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@SuppressLint("ModifierParameter")
@Composable
fun LoadingView(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = modifier.then(
            Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        )
    )
}