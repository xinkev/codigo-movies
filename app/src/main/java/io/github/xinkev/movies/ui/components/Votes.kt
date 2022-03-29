package io.github.xinkev.movies.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

@Composable
fun Votes(voteCount: Int, voted: Boolean, onVoteClick: (voted: Boolean) -> Unit) {
    val icon = if (voted) Icons.Default.Favorite else Icons.Default.FavoriteBorder
    Row {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.Red,
            modifier = Modifier.clickable(onClick = { onVoteClick(!voted) }, role = Role.Switch)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = "$voteCount")
    }
}