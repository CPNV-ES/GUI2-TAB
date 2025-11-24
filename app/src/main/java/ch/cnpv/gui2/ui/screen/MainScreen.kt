package ch.cnpv.gui2.ui.screen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.carousel.HorizontalUncontainedCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ch.cnpv.gui2.R
import ch.cnpv.gui2.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(onNavigateOtherClick: () -> Unit) {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf("Accueil", "Publier", "Recherche")
    val icons = listOf(Icons.Filled.Menu, Icons.Outlined.AddCircle, Icons.Filled.Search)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
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
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Profil"
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Menu"
                        )
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
                        onClick = { selectedItem = index }
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            CarouselStories()
            Text("MainScreen - Onglet: ${items[selectedItem]}")
            Button(
                onClick = { onNavigateOtherClick() },
            ) {
                Text("Navigate to other screen")
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarouselStories() {
    data class CarouselItem(
        val id: Int,
        @DrawableRes val imageResId: Int,
        val contentDescription: String
    )

    val carouselItems = remember {
        listOf(
            CarouselItem(0, R.drawable.profile1, "cupcake"),
            CarouselItem(1, R.drawable.profile1, "donut"),
            CarouselItem(2, R.drawable.profile1, "eclair"),
            CarouselItem(3, R.drawable.profile1, "froyo"),
            CarouselItem(4, R.drawable.profile1, "gingerbread"),
        )
    }

    HorizontalUncontainedCarousel(
        state = rememberCarouselState { carouselItems.count() },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 16.dp, bottom = 16.dp),
        itemWidth = 100.dp,
        itemSpacing = 8.dp,
    ) { i ->
        val item = carouselItems[i]
        Image(
            modifier = Modifier
                .height(100.dp),
            painter = painterResource(id = item.imageResId),
            contentDescription = item.contentDescription,
            contentScale = ContentScale.Fit
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    AppTheme {
        MainScreen(onNavigateOtherClick = {})
    }
}