package ch.cnpv.gui2.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ch.cnpv.gui2.data.Data
import ch.cnpv.gui2.ui.screen.MainScreen
import ch.cnpv.gui2.ui.screen.OtherScreen
import ch.cnpv.gui2.ui.screen.DetailScreen
import kotlinx.serialization.Serializable

@Serializable
object Main

@Serializable
object Other

@Serializable
data class Detail(val postId: Int)

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = Main) {
        composable<Main> {
            MainScreen(
                posts = Data.posts,
                onPostClick = { post ->
                    navController.navigate(Detail(post.id))
                },
                onNavigateOtherClick = {
                    navController.navigate(Other)
                }
            )
        }

        composable<Detail> { backStackEntry ->
            val postId = backStackEntry.arguments?.getInt("postId")
                ?: return@composable

            val post = Data.posts.first { it.id == postId }

            DetailScreen(
                post = post,
                onBackClick = { navController.navigateUp() }
            )
        }

        composable<Other> {
            OtherScreen(
                onBackClick = { navController.navigateUp() }
            )
        }
    }
}
