package ch.cnpv.gui2.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import ch.cnpv.gui2.R
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade

/**
 * Composable pour afficher une image depuis une URL avec gestion d'erreur
 * @param imageUrl L'URL de l'image
 * @param contentDescription Description pour l'accessibilité
 * @param modifier Modificateurs Compose
 * @param contentScale Comment l'image doit être mise à l'échelle
 * @param placeholderRes Ressource drawable pour le placeholder (par défaut: default_placeholder)
 */
@Composable
fun NetworkImage(
    imageUrl: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    placeholderRes: Int = R.drawable.default_placeholder
) {
    if (imageUrl.isNullOrBlank()) {
        // Utiliser le placeholder si pas d'URL
        Image(
            painter = painterResource(placeholderRes),
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = modifier
        )
    } else {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = modifier,
            placeholder = painterResource(placeholderRes),
            error = painterResource(placeholderRes)
        )
    }
}

/**
 * Composable spécifique pour les images de profil
 */
@Composable
fun ProfileImage(
    imageUrl: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop
) {
    NetworkImage(
        imageUrl = imageUrl,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale,
        placeholderRes = R.drawable.default_placeholder
    )
}

/**
 * Composable spécifique pour les images de post
 */
@Composable
fun PostImage(
    imageUrl: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop
) {
    NetworkImage(
        imageUrl = imageUrl,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale,
        placeholderRes = R.drawable.duck_placeholder
    )
}