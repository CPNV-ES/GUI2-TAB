package ch.cnpv.gui2.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ch.cnpv.gui2.ui.theme.AppTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import ch.cnpv.gui2.R


@Composable
fun StoryScreen(imageRes: Int?, progress : Float) {
    val painter = if (imageRes != null) {
        painterResource(imageRes)
    } else {
        painterResource(R.drawable.default_placeholder)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        StoryProgressBar(
            progress = progress,
            modifier = Modifier.align(Alignment.TopCenter).padding(top = 12.dp)
        )

    }
}

@Composable
fun StoryProgressBar(progress: Float, modifier: Modifier = Modifier) {
    LinearProgressIndicator(
        progress = progress,
        modifier = modifier.fillMaxWidth().height(4.dp),
        color = Color.White,
        trackColor = Color.Gray.copy(alpha = 0.3f)
    )
}

@Preview(showBackground = true)
@Composable
fun StoryScreenPreview() {
    AppTheme {
        StoryScreen(imageRes = null, progress = 0.5f)
    }
}