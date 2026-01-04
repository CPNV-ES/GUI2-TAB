package ch.cnpv.gui2.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ch.cnpv.gui2.models.Post
import ch.cnpv.gui2.network.RetrofitInstance
import ch.cnpv.gui2.ui.screen.MainScreen
import ch.cnpv.gui2.ui.screen.DetailScreen
import ch.cnpv.gui2.ui.screen.StoryScreen
import kotlinx.serialization.Serializable

@Serializable
object Main

@Serializable
data class Detail(val postId: String)

@Serializable
data class Story(val postId: String)

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    var posts by remember { mutableStateOf<List<Post>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        try {
            val aliceId = "11111111-1111-1111-1111-111111111111"

            val friends = RetrofitInstance.api.getFriends(aliceId)

            val allPosts = mutableListOf<Post>()
            friends.forEach { friend ->
                try {
                    val friendPosts = RetrofitInstance.api.getPosts(friend.id)
                    allPosts.addAll(friendPosts)
                } catch (e: Exception) {
                }
            }

            posts = allPosts
            isLoading = false
        } catch (e: Exception) {
            isLoading = false
        }
    }

    NavHost(navController, startDestination = Main) {
        composable<Main> {
            MainScreen(
                posts = posts,
                isLoading = isLoading,
                onPostClick = { post ->
                    navController.navigate(Detail(post.id))
                },
                onStoryClick = { post ->
                    navController.navigate(Story(post.id))
                }
            )
        }

        composable<Detail> { backStackEntry ->
            val detail = backStackEntry.arguments?.getString("postId") ?: return@composable
            val post = posts.firstOrNull { it.id == detail }

            if (post != null) {
                DetailScreen(
                    post = post,
                    onBackClick = { navController.navigateUp() }
                )
            }
        }

        composable<Story> { backStackEntry ->
            val storyId = backStackEntry.arguments?.getString("postId") ?: return@composable
            val post = posts.firstOrNull { it.id == storyId }

            if (post != null) {
                StoryScreen(
                    post = post,
                    onFinished = { navController.navigateUp() }
                )
            }
        }
    }
}