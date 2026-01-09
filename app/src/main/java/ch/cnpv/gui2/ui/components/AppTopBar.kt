package ch.cnpv.gui2.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow
import ch.cnpv.gui2.models.Profil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    currentProfil: Profil?,
    availableProfils: List<Profil>,
    onProfilSelected: (Profil) -> Unit,
    onBackClick: (() -> Unit)? = null
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(
                "Duck Duck Social",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        actions = {
            ProfileSwitcher(
                currentProfil = currentProfil,
                availableProfils = availableProfils,
                onProfilSelected = onProfilSelected
            )
        },
        navigationIcon = {
            IconButton(onClick = { onBackClick?.invoke() ?: run {} }) {
                Icon(
                    imageVector = if (onBackClick != null) Icons.Filled.ArrowBack else Icons.Filled.Menu,
                    contentDescription = if (onBackClick != null) "Retour" else "Menu"
                )
            }
        },
    )
}