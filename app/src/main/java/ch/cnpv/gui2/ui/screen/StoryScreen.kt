package ch.cnpv.gui2.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ch.cnpv.gui2.ui.theme.AppTheme
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale


@Composable
fun DuckStory(imageRes: Int?) {
    val painter = if (imageRes != null) {
        painterResource(id = imageRes)
    } else {
        painterResource(id = R.drawable.ic_placeholder)
    }

    Image(
        painter = painter,
        contentDescription = null
    )
}

@Preview(showBackground = true)
@Composable
fun DuckStoryPreview() {
    AppTheme {
        DuckStory(imageRes = R.drawable.ic_placeholder)
    }
}
