package ch.cnpv.gui2.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ch.cnpv.gui2.data.ProfilsData
import ch.cnpv.gui2.models.Post
import ch.cnpv.gui2.models.Profil
import ch.cnpv.gui2.network.RetrofitInstance
import ch.cnpv.gui2.ui.screen.MainScreen
import ch.cnpv.gui2.ui.screen.DetailScreen
import ch.cnpv.gui2.ui.screen.ProfilScreen
import ch.cnpv.gui2.ui.screen.PostFormScreen
import ch.cnpv.gui2.ui.screen.PostFormMode
import ch.cnpv.gui2.ui.screen.StoryScreen
import kotlinx.serialization.Serializable

@Serializable
object Main

@Serializable
object CreatePost

@Serializable
data class EditPost(val postId: String)

@Serializable
data class Detail(val postId: String)

@Serializable
data class Story(val postId: String)

@Serializable
object ProfilScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    var feedPosts by remember { mutableStateOf<List<Post>>(emptyList()) }
    var myPosts by remember { mutableStateOf<List<Post>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val availableProfils = ProfilsData.allProfils
    var currentProfil by remember { mutableStateOf<Profil>(ProfilsData.getDefaultProfil()) }

    LaunchedEffect(currentProfil) {
        isLoading = true
        errorMessage = null

        try {
            myPosts = try {
                RetrofitInstance.api.getPosts(currentProfil.id)
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }

            val friends = try {
                RetrofitInstance.api.getFriends(currentProfil.id)
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }

            feedPosts = friends.flatMap { friend ->
                try {
                    RetrofitInstance.api.getPosts(friend.id)
                } catch (e: Exception) {
                    e.printStackTrace()
                    emptyList()
                }
            }
        } catch (e: Exception) {
            errorMessage = "Erreur: ${e.message}"
            e.printStackTrace()
        } finally {
            isLoading = false
        }
    }

    NavHost(navController, startDestination = Main) {
        composable<Main> {
            MainScreen(
                posts = feedPosts,
                isLoading = isLoading,
                errorMessage = errorMessage,
                currentProfil = currentProfil,
                availableProfils = availableProfils,
                onProfilSelected = { profil ->
                    currentProfil = profil
                },
                onPostClick = { post ->
                    navController.navigate(Detail(post.id))
                },
                onStoryClick = { post ->
                    navController.navigate(Story(post.id))
                },
                onProfilClick = {
                    navController.navigate(ProfilScreen)
                },
                onPublishClick = {
                    navController.navigate(CreatePost)
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
                    currentProfil = currentProfil,
                    availableProfils = availableProfils,
                    onProfilSelected = { profil ->
                        currentProfil = profil
                    },
                    onBackClick = { navController.navigateUp() }
                )
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Post non trouvé")
                }
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
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Story non trouvée")
                }
            }
        }

        composable<ProfilScreen> {
            ProfilScreen(
                posts = myPosts,
                currentProfil = currentProfil,
                availableProfils = availableProfils,
                onProfilSelected = { profil ->
                    currentProfil = profil
                },
                onNavigateHome = {
                    navController.navigate(Main) {
                        popUpTo(Main) { inclusive = false }
                    }
                },
                onNavigatePublish = {
                    navController.navigate(CreatePost)
                },
                onPostClick = { post ->
                    navController.navigate(Detail(post.id))
                },
                onEdit = { post ->
                    navController.navigate(EditPost(post.id))
                },
                onDelete = { deletedPost ->
                    myPosts = myPosts.filter { it.id != deletedPost.id }
                }
            )
        }

        composable<CreatePost> {
            PostFormScreen(
                mode = PostFormMode.Create,
                currentProfil = currentProfil,
                availableProfils = availableProfils,
                onProfilSelected = { profil ->
                    currentProfil = profil
                },
                onSuccess = { newPost ->
                    myPosts = listOf(newPost) + myPosts
                },
                onNavigateHome = {
                    navController.navigate(Main) {
                        popUpTo(Main) { inclusive = false }
                    }
                },
                onNavigateProfil = {
                    navController.navigate(ProfilScreen)
                }
            )
        }

        composable<EditPost> { backStackEntry ->
            val postId = backStackEntry.arguments?.getString("postId") ?: return@composable
            val post = myPosts.firstOrNull { it.id == postId }

            if (post != null) {
                PostFormScreen(
                    mode = PostFormMode.Edit(post),
                    currentProfil = currentProfil,
                    availableProfils = availableProfils,
                    onProfilSelected = { profil ->
                        currentProfil = profil
                    },
                    onSuccess = { updatedPost ->
                        myPosts = myPosts.map {
                            if (it.id == updatedPost.id) updatedPost else it
                        }
                    },
                    onNavigateHome = {
                        navController.navigate(ProfilScreen) {
                            popUpTo(ProfilScreen) { inclusive = false }
                        }
                    },
                    onNavigateProfil = {
                        navController.navigate(ProfilScreen)
                    }
                )
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Post non trouvé")
                }
            }
        }
    }
}