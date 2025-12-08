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
import androidx.compose.runtime.*
import kotlinx.coroutines.delay
import ch.cnpv.gui2.R
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember


@Composable
fun StoryScreen(
    imageRes: Int?,
    steps: Int = 4,
    currentStep: Int = 0,
    onFinished: () -> Unit = {}
) {
    val painter = if (imageRes != null) {
        painterResource(imageRes)
    } else {
        painterResource(R.drawable.default_placeholder)
    }

    var step by remember { mutableStateOf(currentStep) }
    var progress by remember { mutableStateOf(0f) }
    val isPressed = remember { mutableStateOf(false)}
    val previousScreen = {}
    val nextScreen = {}

    LaunchedEffect(step) {
        progress = 0f
        while (progress < 1f) {
            delay(50)
            progress += 0.01f
        }
        if (step < steps - 1) {
            step++
        } else {
            onFinished()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize().pointerInput(Unit) {
                detectTapGestures(
                    onPress = { offset ->
                        val maxWidth = size.width
                        val pressStartTime = System.currentTimeMillis()
                        isPressed.value = true
                        tryAwaitRelease()
                        val pressEndTime = System.currentTimeMillis()
                        val totalPressTime = pressEndTime - pressStartTime

                        if (totalPressTime < 200) {
                            val isTapOnRightTwoTiers = offset.x > (maxWidth / 4f)

                            if (isTapOnRightTwoTiers) {
                                nextScreen()
                            } else {
                                previousScreen()
                            }
                        }
                        isPressed.value = false
                    }
                )
            }
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        StoryProgressBar(
            steps = steps,
            currentStep = step,
            progress = progress,
            modifier = Modifier.align(Alignment.TopCenter).padding(top = 12.dp)
        )
    }
}

@Composable
fun StoryProgressBar(
    steps: Int,
    currentStep: Int,
    progress: Float,
    modifier: Modifier = Modifier
)
{
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        repeat(steps) { index ->
            val barProgress = when {
                index < currentStep -> 1f
                index == currentStep -> progress
                else -> 0f
            }

            LinearProgressIndicator(
                progress = barProgress,
                modifier = Modifier.weight(1f).height(4.dp),
                color = Color.White,
                trackColor = Color.Gray.copy(alpha = 0.3f)
            )
        }
    }
}




@Preview(showBackground = true)
@Composable
fun StoryScreenPreview() {
    AppTheme {
        StoryScreen(
            imageRes = null
        )
    }
}