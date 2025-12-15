package ch.cnpv.gui2.data

import ch.cnpv.gui2.R
import ch.cnpv.gui2.models.*

object Data {
    val profils = listOf(
        Profil("Alice", image = R.drawable.salut),
        Profil("Bob", image = R.drawable.salut)
    )

    val posts = listOf(
        Post(
            id = 1,
            profil = profils[0],
            description = "Mon premier post",
            topic = "Android",
            image = R.drawable.salut,
            comments = listOf(
                Comment(profils[1], "Super post !"),
                Comment(profils[0], "Merci 😄")
            )
        ),
        Post(
            id = 2,
            profil = profils[1],
            description = "Jetpack Compose c'est génial",
            topic = "Compose",
            image = R.drawable.salut,
            comments = listOf(
                Comment(profils[0], "Totalement d'accord")
            )
        )
    )
}
