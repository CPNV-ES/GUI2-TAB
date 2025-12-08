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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.draw.clip
import androidx.compose.material3.Text

import ch.cnpv.gui2.R


@Composable
fun StoryScreen(
    imageRes: Int?,
    steps: Int = 4,
    currentStep: Int = 0,
    onFinished: () -> Unit = {}
) {
    val painterStory = if (imageRes != null) {
        painterResource(imageRes)
    } else {
        painterResource(R.drawable.default_placeholder)
    }

    val painterAvatar = if (imageRes != null) {
        painterResource(imageRes)
    } else {
        painterResource(R.drawable.default_placeholder)
    }

    var step by remember { mutableStateOf(currentStep) }
    var progress by remember { mutableStateOf(0f) }
    val isPressed = remember { mutableStateOf(false)}
    val previousScreen = {
        if (step > 0) step --
    }
    val nextScreen = {
        if (step < steps -1) step ++
    }

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
    Scaffold() {
        paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize().padding(paddingValues)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = { offset ->
                            val maxWidth = size.width
                            val pressStartTime = System.currentTimeMillis()
                            isPressed.value = true
                            tryAwaitRelease()
                            val pressEndTime = System.currentTimeMillis()
                            val totalPressTime = pressEndTime - pressStartTime

                            if (totalPressTime < 200) {
                                val isTapOnRightTwoTiers = offset.x > (maxWidth / 2f)

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
                painter = painterStory,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Row(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 25.dp, start = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterAvatar,
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = "Username",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )
            }

            StoryProgressBar(
                steps = steps,
                currentStep = step,
                progress = progress,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 10.dp)
            )
        }
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
                progress = {barProgress},
                modifier = Modifier.weight(1f)
                    .height(4.dp),
                color = Color.White,
                trackColor = Color.Gray.copy(alpha = 0.3f),
                drawStopIndicator = {}
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