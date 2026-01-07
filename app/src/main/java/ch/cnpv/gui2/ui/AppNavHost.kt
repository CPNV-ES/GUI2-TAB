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
import ch.cnpv.gui2.ui.screen.MyPostsScreen
import ch.cnpv.gui2.ui.screen.PublishScreen
import ch.cnpv.gui2.ui.screen.StoryScreen
import kotlinx.serialization.Serializable

@Serializable
object Main

@Serializable
object Publish

@Serializable
data class Detail(val postId: String)

@Serializable
data class Story(val postId: String)

@Serializable
object MyPosts

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    var feedPosts by remember { mutableStateOf<List<Post>>(emptyList()) }
    var myPosts by remember { mutableStateOf<List<Post>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        try {
            val aliceId = "11111111-1111-1111-1111-111111111111"

            myPosts = RetrofitInstance.api.getPosts(aliceId)

            val friends = RetrofitInstance.api.getFriends(aliceId)
            val friendsPosts = friends.flatMap { friend ->
                try {
                    RetrofitInstance.api.getPosts(friend.id)
                } catch (e: Exception) {
                    emptyList()
                }
            }

            feedPosts = friendsPosts

        } catch (e: Exception) {
            feedPosts = emptyList()
            myPosts = emptyList()
        } finally {
            isLoading = false
        }
    }



    NavHost(navController, startDestination = Main) {
        composable<Main> {
            MainScreen(
                posts = feedPosts,
                isLoading = isLoading,
                onPostClick = { post ->
                    navController.navigate(Detail(post.id))
                },
                onStoryClick = { post ->
                    navController.navigate(Story(post.id))
                },
                onMyPostsClick = {
                    navController.navigate(MyPosts)
                },
                },
                onPublishClick = {
                    navController.navigate(Publish)
                }
            )
        }

        composable<Detail> { backStackEntry ->
            val detail = backStackEntry.arguments?.getString("postId") ?: return@composable
            val allPosts = feedPosts + myPosts

            val post = allPosts.firstOrNull { it.id == detail }

            if (post != null) {
                DetailScreen(
                    post = post,
                    onBackClick = { navController.navigateUp() }
                )
            }
        }

        composable<Story> { backStackEntry ->
            val storyId = backStackEntry.arguments?.getString("postId") ?: return@composable
            val post = feedPosts.firstOrNull { it.id == storyId }

            if (post != null) {
                StoryScreen(
                    post = post,
                    onFinished = { navController.navigateUp() }
                )
            }
        }

        composable<MyPosts> {
            MyPostsScreen(
                posts = myPosts,
                onBackClick = { navController.navigateUp() },
                onPostClick = { post ->
                    navController.navigate(Detail(post.id))
                },
                onDelete = { deletedPost ->
                    myPosts = myPosts.filter { it.id != deletedPost.id }
                }
            )
        }
        composable<Publish> {
            PublishScreen(
                isLoading = false,
                onSend = { println(it) }
            )
        }
    }
}