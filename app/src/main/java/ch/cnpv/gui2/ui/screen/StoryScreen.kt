package ch.cnpv.gui2.ui.screen

import android.widget.ProgressBar
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import ch.cnpv.gui2.R


@Composable
fun DuckStory(imageRes: Int?, progress : Float) {
    val painter = if (imageRes != null) {
        painterResource(imageRes)
    } else {
        painterResource(R.drawable.default_placeholder)
    }

    Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )

    StoryProgressBar(progress = progress)

}

@Composable
fun StoryProgressBar(progress: Float) {
    LinearProgressIndicator(
        progress = progress,
        modifier = Modifier.fillMaxWidth().height(4.dp),
        color = Color.White,
        trackColor = Color.Gray.copy(alpha = 0.3f)
    )
}

@Preview(showBackground = true)
@Composable
fun DuckStoryPreview() {
    AppTheme {
        DuckStory(imageRes = null, progress = 0.5f)
    }
}
