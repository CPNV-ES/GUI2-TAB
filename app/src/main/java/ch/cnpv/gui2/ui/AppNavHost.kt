package ch.cnpv.gui2.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ch.cnpv.gui2.data.Data
import ch.cnpv.gui2.ui.screen.MainScreen
import ch.cnpv.gui2.ui.screen.DetailScreen
import ch.cnpv.gui2.ui.screen.StoryScreen
import kotlinx.serialization.Serializable

@Serializable
object Main

@Serializable
object Other

@Serializable
data class Detail(val postId: Int)

@Serializable
data class Story(val postId: Int)

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
                onStoryClick = { post ->
                    navController.navigate(Story(post.id))
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

        composable<Story> { backStackEntry ->
            val postId = backStackEntry.arguments?.getInt("postId") ?: return@composable
            val post = Data.posts.first { it.id == postId }

            StoryScreen(
                post = post,
                onFinished = { navController.navigateUp() }
            )
        }
    }
}
