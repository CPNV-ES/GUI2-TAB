package ch.cnpv.gui2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import ch.cnpv.gui2.ui.AppNavHost
import ch.cnpv.gui2.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            AppTheme {
                /**
                 * This surface prevents the activity background color from showing through when
                 * no composable is being shown (e.g. navigating between two screens).
                 * The default color for this surface is set to the theme's background color.
                 */
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    AppNavHost()
                }
            }
        }
    }
}
