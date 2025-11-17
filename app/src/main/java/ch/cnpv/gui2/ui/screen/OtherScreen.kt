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
fun OtherScreen(onBackClick: () -> Unit) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Text("OtherScreen")
            Button(
                onClick = { onBackClick() }
            ) {
                Text("Go back")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OtherScreenPreview() {
    AppTheme {
        OtherScreen(onBackClick = {})
    }
}