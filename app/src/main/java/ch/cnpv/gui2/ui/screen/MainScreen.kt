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

@Composable
fun MainScreen(onNavigateOtherClick: () -> Unit) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Text("MainScreen")
            Button(
                onClick = { onNavigateOtherClick() },
            ) {
                Text("Navigate to other screen")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    AppTheme {
        MainScreen(onNavigateOtherClick = {})
    }
}