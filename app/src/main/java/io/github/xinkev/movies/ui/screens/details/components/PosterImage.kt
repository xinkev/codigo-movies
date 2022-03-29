package io.github.xinkev.movies.ui.screens.details.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage
import io.github.xinkev.movies.database.entities.MovieDetails
import io.github.xinkev.movies.ui.components.LoadingView
import io.github.xinkev.movies.utils.imageUrl

@Composable
fun PosterImage(path: String?) {
    GlideImage(
        imageModel = imageUrl(path),
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        circularReveal = CircularReveal(),
        loading = {
            LoadingView()
        },
        failure = {
            Icon(
                imageVector = Icons.Default.BrokenImage,
                contentDescription = "Broken Image",
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        },
    )
}