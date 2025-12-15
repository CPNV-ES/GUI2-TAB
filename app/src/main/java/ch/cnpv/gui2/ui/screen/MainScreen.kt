package ch.cnpv.gui2.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ch.cnpv.gui2.data.Data
import ch.cnpv.gui2.models.Post
import ch.cnpv.gui2.ui.theme.AppTheme

@Composable
fun MainScreen(
    posts: List<Post>,
    onPostClick: (Post) -> Unit,
    onNavigateOtherClick: () -> Unit,
) {
    Scaffold { padding ->
        Column(modifier = Modifier.padding(padding)) {
            Button(
                onClick = { onNavigateOtherClick() },
            ) {
                Text("Navigate to other screen")
            }
            LazyColumn(modifier = Modifier.padding(padding)) {
                items(posts) { post ->
                    Column() {
                        Text(text = post.profil.username)
                        Text(text = post.description)

                        Button(onClick = { onPostClick(post) }) {
                            Text("Voir détails")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    AppTheme {
        MainScreen(
            posts = Data.posts,
            onPostClick = {},
            onNavigateOtherClick = {},
        )
    }
}
