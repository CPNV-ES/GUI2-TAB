package ch.cnpv.gui2.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ch.cnpv.gui2.ui.screen.MainScreen
import ch.cnpv.gui2.ui.screen.OtherScreen
import kotlinx.serialization.Serializable

@Serializable
object Main

@Serializable
object Other

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = Main) {
        composable<Main> {
            MainScreen(
                onNavigateOtherClick = { navController.navigate(Other) }
            )
        }
        composable<Other> {
            OtherScreen(
                onBackClick = { navController.navigateUp() }
            )
        }
    }
}
