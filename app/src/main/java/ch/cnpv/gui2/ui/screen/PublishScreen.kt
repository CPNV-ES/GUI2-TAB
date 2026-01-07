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
import ch.cnpv.gui2.ui.viewmodel.PublishViewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublishScreen(
    onSuccess: (Post) -> Unit = {},
    onNavigateHome: () -> Unit = {},
    onNavigateProfil: () -> Unit = {},
    viewModel: PublishViewModel = viewModel()
) {
    var selectedItem by remember { mutableIntStateOf(1) }
    val items = listOf("Accueil", "Publier", "Profil")
    val icons = listOf(Icons.Filled.Home, Icons.Outlined.AddCircle, Icons.Filled.AccountCircle)

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
                    IconButton(onClick = { onNavigateProfil() }) {
                        Icon(Icons.Default.AccountCircle, contentDescription = "Profil")
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { }) {
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
                                0 -> onNavigateHome()
                                2 -> onNavigateProfil()
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        PublishForm(
            viewModel = viewModel,
            onSuccess = onSuccess,
            onBackClick = onNavigateHome,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}

@Composable
fun PublishForm(
    viewModel: PublishViewModel,
    onSuccess: (Post) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    var description by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    LaunchedEffect(uiState.publishedPost) {
        if (uiState.publishedPost != null) {
            onSuccess(uiState.publishedPost!!)
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
            text = "Créer une publication",
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

        if (imageUri != null) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(imageUri)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Image sélectionnée",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
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
            Text(if (imageUri == null) "Ajouter une image" else "Changer l'image")
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
                if (imageUri != null) {
                    viewModel.publishPost(
                        context = context,
                        description = description,
                        imageUri = imageUri!!
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = description.isNotBlank() && imageUri != null && !uiState.isLoading
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(20.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            Text(if (uiState.isLoading) "Publication en cours..." else "Publier")
        }
    }
}