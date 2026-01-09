package ch.cnpv.gui2.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import ch.cnpv.gui2.models.Profil

@Composable
fun ProfileSwitcher(
    currentProfil: Profil?,
    availableProfils: List<Profil>,
    onProfilSelected: (Profil) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        IconButton(onClick = { expanded = true }) {
            if (currentProfil != null && currentProfil.imageUrl.isNotEmpty()) {
                ProfileImage(
                    imageUrl = currentProfil.imageUrl,
                    contentDescription = "Profil actuel: ${currentProfil.name}",
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Changer de profil"
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            availableProfils.forEach { profil ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = if (profil.id == currentProfil?.id)
                                "${profil.name} ✓"
                            else
                                profil.name
                        )
                    },
                    onClick = {
                        onProfilSelected(profil)
                        expanded = false
                    }
                )
            }
        }
    }
}