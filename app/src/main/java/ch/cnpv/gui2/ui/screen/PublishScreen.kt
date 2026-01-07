package ch.cnpv.gui2.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.material3.TextField
import ch.cnpv.gui2.models.Post
import ch.cnpv.gui2.models.Profil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublishScreen(
    isLoading: Boolean = false,
    onSend: (Post) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    var selectedItem by remember { mutableIntStateOf(1) }
    val items = listOf("Accueil", "Publier", "Mes edits")
    val icons = listOf(
        Icons.Filled.Menu,
        Icons.Outlined.AddCircle,
        Icons.Filled.Edit
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text("Duck Duck Social") },
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(Icons.Default.AccountCircle, contentDescription = "Profil")
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menu")
                    }
                },
            )
        },
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(icons[index], contentDescription = item) },
                        label = { Text(item) },
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index

                            when (index) {
                                0 -> onBackClick()
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                PublishForm(
                    onSend = onSend,
                    modifier = Modifier.fillMaxWidth(0.9f)
                )
            }
        }
    }
}


@Composable
fun PublishForm(
    onSend: (Post) -> Unit,
    modifier: Modifier = Modifier
) {
    var text by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Description de votre post") },
            singleLine = true,
            shape = RoundedCornerShape(24.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            val randomImageId = (1..100).random()
            imageUrl = "https://picsum.photos/seed/$randomImageId/400/300"
        }) {
            Text(if (imageUrl == null) "Ajouter une image" else "Image sélectionnée")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (text.isNotBlank() && imageUrl != null) {
                    val currentTime = java.time.Instant.now().toString()
                    val aliceProfil = Profil(
                        id = "11111111-1111-1111-1111-111111111111",
                        name = "Alice",
                        imageUrl = "https://i.pravatar.cc/150?img=1",
                        hash = null,
                        createdAt = currentTime,
                        updatedAt = currentTime
                    )

                    val newPost = Post(
                        id = java.util.UUID.randomUUID().toString(),
                        description = text,
                        imageUrl = imageUrl!!,
                        profil = aliceProfil,
                        createdAt = currentTime,
                        updatedAt = currentTime
                    )
                    onSend(newPost)
                    text = ""
                    imageUrl = null
                }
            },
            enabled = text.isNotBlank() && imageUrl != null
        ) {
            Text("Publier")
        }
    }
}