package ch.cnpv.gui2.ui.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ch.cnpv.gui2.models.Post
import ch.cnpv.gui2.models.Profil
import ch.cnpv.gui2.ui.components.PostImage
import ch.cnpv.gui2.ui.viewmodel.PostFormViewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostFormScreen(
    mode: PostFormMode,
    currentProfil: Profil?,
    availableProfils: List<Profil>,
    onProfilSelected: (Profil) -> Unit,
    onSuccess: (Post) -> Unit = {},
    onNavigateHome: () -> Unit = {},
    onNavigateProfil: () -> Unit = {},
    viewModel: PostFormViewModel = viewModel()
) {
    val selectedItem = when (mode) {
        is PostFormMode.Create -> 1
        is PostFormMode.Edit -> 2
    }
    val items = listOf("Accueil", "Publier", "Profil")
    val icons = listOf(Icons.Filled.Home, Icons.Outlined.AddCircle, Icons.Filled.AccountCircle)

    LaunchedEffect(mode) {
        if (mode is PostFormMode.Edit) {
            viewModel.setEditMode(mode.post)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            ch.cnpv.gui2.ui.components.AppTopBar(
                currentProfil = currentProfil,
                availableProfils = availableProfils,
                onProfilSelected = onProfilSelected
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
                            when (index) {
                                0 -> onNavigateHome()
                                2 -> onNavigateProfil()
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        PostForm(
            viewModel = viewModel,
            currentProfil = currentProfil,
            mode = mode,
            onSuccess = onSuccess,
            onBackClick = onNavigateHome,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}

@Composable
fun PostForm(
    viewModel: PostFormViewModel,
    currentProfil: Profil?,
    mode: PostFormMode,
    onSuccess: (Post) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    var description by remember { mutableStateOf(
        if (mode is PostFormMode.Edit) mode.post.description else ""
    ) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var hasImageChanged by remember { mutableStateOf(false) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
        hasImageChanged = true
    }

    LaunchedEffect(uiState.savedPost) {
        if (uiState.savedPost != null) {
            onSuccess(uiState.savedPost!!)
            description = ""
            imageUri = null
            viewModel.resetState()
            onBackClick()
        }
    }

    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = when (mode) {
                is PostFormMode.Create -> "Créer une publication"
                is PostFormMode.Edit -> "Modifier la publication"
            },
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        TextField(
            value = description,
            onValueChange = { description = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Que voulez-vous partager ?") },
            minLines = 3,
            maxLines = 5,
            shape = RoundedCornerShape(12.dp),
            enabled = !uiState.isLoading
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (imageUri != null || (mode is PostFormMode.Edit && !hasImageChanged)) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                if (imageUri != null) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(imageUri)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Image sélectionnée",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else if (mode is PostFormMode.Edit) {
                    PostImage(
                        imageUrl = mode.post.imageUrl,
                        contentDescription = "Image actuelle",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        OutlinedButton(
            onClick = { imagePickerLauncher.launch("image/*") },
            modifier = Modifier.fillMaxWidth(),
            enabled = !uiState.isLoading
        ) {
            Icon(
                imageVector = Icons.Default.AddCircle,
                contentDescription = null,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                when {
                    imageUri != null -> "Changer l'image"
                    mode is PostFormMode.Edit && !hasImageChanged -> "Changer l'image"
                    else -> "Ajouter une image"
                }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (uiState.errorMessage != null) {
            Text(
                text = uiState.errorMessage!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                when (mode) {
                    is PostFormMode.Create -> {
                        if (imageUri != null && currentProfil != null) {
                            viewModel.createPost(
                                context = context,
                                description = description,
                                imageUri = imageUri!!,
                                profilId = currentProfil.id
                            )
                        }
                    }
                    is PostFormMode.Edit -> {
                        if (currentProfil != null) {
                            viewModel.updatePost(
                                context = context,
                                postId = mode.post.id,
                                description = description,
                                imageUri = imageUri,
                                profilId = currentProfil.id
                            )
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = when (mode) {
                is PostFormMode.Create -> description.isNotBlank() && imageUri != null && !uiState.isLoading && currentProfil != null
                is PostFormMode.Edit -> description.isNotBlank() && !uiState.isLoading && currentProfil != null
            }
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(20.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            Text(
                when {
                    uiState.isLoading -> when (mode) {
                        is PostFormMode.Create -> "Publication en cours..."
                        is PostFormMode.Edit -> "Modification en cours..."
                    }
                    else -> when (mode) {
                        is PostFormMode.Create -> "Publier"
                        is PostFormMode.Edit -> "Modifier"
                    }
                }
            )
        }
    }
}

sealed class PostFormMode {
    object Create : PostFormMode()
    data class Edit(val post: Post) : PostFormMode()
}