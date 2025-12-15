package ch.cnpv.gui2.ui.screen

import android.graphics.pdf.models.ListItem
import androidx.compose.foundation.Image
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
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import ch.cnpv.gui2.ui.theme.AppTheme
import ch.cnpv.gui2.R
import ch.cnpv.gui2.data.Data
import ch.cnpv.gui2.models.Post


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    post: Post,
    onBackClick: () -> Unit) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

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
                        Image(
                            painter = painterResource(post.image),
                            contentDescription = "Exemple d'image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
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
                    modifier = Modifier.padding(20.dp)
                        .fillMaxSize()
                ) {
                    Row(

                    ) {
                        IconButton(
                            onClick = { /* do something */ }) {
                            Image(
                                painter = painterResource(post.image),
                                contentDescription = "Exemple d'image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }
                        Column(
                            modifier = Modifier.padding(start = 10.dp)
                        ) {
                            Text(text = post.topic,
                                fontWeight = FontWeight.Bold)
                            Text(text = post.profil.username)
                        }
                    }
                    Text(text = post.description,
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        color = Color.Gray,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(8.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.salut),
                        contentDescription = "Exemple d'image",
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
                    text = "Commentaires (${post.comments.size})",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                if (post.comments.isEmpty()) {
                    Text(
                        text = "Aucun commentaire pour l’instant",
                        color = MaterialTheme.colorScheme.outline
                    )
                } else {
                    post.comments.forEach { comment ->
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
                                    text = comment.profil.username,
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

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    AppTheme {
        DetailScreen(
            post = Data.posts.first(),
            onBackClick = {}
        )
    }
}