package ch.cnpv.gui2.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import ch.cnpv.gui2.models.Comment
import ch.cnpv.gui2.models.Post
import ch.cnpv.gui2.network.RetrofitInstance
import ch.cnpv.gui2.ui.components.ProfileImage
import ch.cnpv.gui2.ui.components.PostImage
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    post: Post,
    onBackClick: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    var comments by remember { mutableStateOf<List<Comment>>(emptyList()) }
    var isLoadingComments by remember { mutableStateOf(false) }

    LaunchedEffect(showBottomSheet) {
        if (showBottomSheet && comments.isEmpty()) {
            isLoadingComments = true
            try {
                comments = RetrofitInstance.api.getComments(post.id)
            } catch (e: Exception) {
            }
            isLoadingComments = false
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text("DucDuc social") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            if (showBottomSheet) {
                                showBottomSheet = false
                            } else {
                                onBackClick()
                            }
                        }
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Profile"
                        )
                    }
                }
            )
        },
        content = { innerPadding ->
            Card(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                shape = RectangleShape,
            ) {
                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxSize()
                ) {
                    Row {
                        ProfileImage(
                            imageUrl = post.profil.imageUrl,
                            contentDescription = "Avatar de ${post.profil.name}",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                        )
                        Column(
                            modifier = Modifier.padding(start = 10.dp)
                        ) {
                            Text(
                                text = post.profil.name,
                                fontWeight = FontWeight.Bold
                            )
                            Text(text = post.createdAt.take(10))
                        }
                    }

                    Text(
                        text = post.description,
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        color = Color.Gray,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(8.dp)
                    )

                    PostImage(
                        imageUrl = post.imageUrl,
                        contentDescription = post.description,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(top = 8.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )
                }
            }
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color.White
            ) {
                Button(
                    onClick = { showBottomSheet = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                    ),
                    shape = ButtonDefaults.shape,
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "Rejoindre la conversation",
                            color = MaterialTheme.colorScheme.outline,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }
    )

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Commentaires (${comments.size})",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                if (isLoadingComments) {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(16.dp)
                    )
                } else if (comments.isEmpty()) {
                    Text(
                        text = "Aucun commentaire pour l'instant",
                        color = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                } else {
                    comments.forEach { comment ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = null,
                                modifier = Modifier.size(40.dp)
                            )
                            Column(modifier = Modifier.padding(start = 8.dp)) {
                                Text(
                                    text = "User ${comment.fromId.take(8)}",
                                    fontWeight = FontWeight.Bold
                                )
                                Text(text = comment.text)
                            }
                        }
                    }
                }
            }
        }
    }
}