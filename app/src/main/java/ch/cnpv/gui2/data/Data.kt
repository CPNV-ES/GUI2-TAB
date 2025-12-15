package ch.cnpv.gui2.data

import ch.cnpv.gui2.R
import ch.cnpv.gui2.models.*

object Data {
    val profils = listOf(
        Profil("Alice", image = R.drawable.salut),
        Profil("Bob", image = R.drawable.label),
        Profil("Charle", image = R.drawable.tigre),
    )

    val posts = listOf(
        Post(
            id = 1,
            profil = profils[0],
            description = "Mon premier post",
            topic = "Android",
            image = R.drawable.suisse,
            comments = listOf(
                Comment(profils[1], "Super post !"),
                Comment(profils[2], "Merci 😄"),
                Comment(profils[0], "Merci 😄"),
                Comment(profils[1], "Merci 😄"),
                Comment(profils[2], "Merci 😄"),
                Comment(profils[0], "Merci 😄"),
                Comment(profils[1], "Merci 😄"),
                Comment(profils[0], "Merci 😄"),
                Comment(profils[2], "Merci 😄"),
                Comment(profils[1], "Merci 😄"),
                Comment(profils[0], "Merci 😄"),
                Comment(profils[0], "Pas d'accord"),
                Comment(profils[1], "Ton thé t'a-t-il ôté ta toux tenace ?"),
                Comment(profils[1], "Oh! Combien de marins, combien de capitaines\n" +
                        "Qui sont partis joyeux pour des courses lointaines\n" +
                        "Dans ce morne horizon se sont évanouis!\n" +
                        "Combien ont disparu, dure et triste fortune!\n" +
                        "Dans une mer sans fond, par une nuit sans lune\n" +
                        "Sous l'aveugle océan à jamais enfouis!\n" +
                        "Combien de patrons morts avec leurs équipages!\n" +
                        "L'ouragan de leur vie a pris toutes les pages\n" +
                        "Et d'un souffle il a tout dispersé sous les flots!\n" +
                        "Nul ne saura jamais leur fin dans l'abîme plongée."),
                Comment(profils[0], "Pas d'accord"),
                Comment(profils[1], "Ton thé t'a-t-il ôté ta toux tenace ?"),
                Comment(profils[1], "Papa boit dans les pins. Papa peint dans les bois. Dans les bois, papa boit et\n" +
                        "peint. Un pêcheur prépare pitance, plaid, pliant, pipe, parapluie, prend panier\n" +
                        "percé pour ne pas perdre petits poissons, place dans poche petit pot parfaite\n" +
                        "piquette, puis part pédestrement pêcher pendant période permise par police."),
            )
        ),
        Post(
            id = 2,
            profil = profils[1],
            description = "Un tigre, c'est génial",
            topic = "Compose",
            image = R.drawable.tigre,
            comments = listOf(
                Comment(profils[0], "Totalement d'accord"),
                Comment(profils[2], "Donnez-lui à minuit huit fruits cuits et si ces huit fruits cuits lui nuisent, donnez-lui huit fruits crus")

            )
        ),
        Post(
            id = 3,
            profil = profils[2],
            description = "Les wombat, c'est génial",
            topic = "Compose",
            image = R.drawable.vombatus,
            comments = listOf(
                Comment(profils[0], "Pas d'accord"),
                Comment(profils[1], "Ton thé t'a-t-il ôté ta toux tenace ?"),
                Comment(profils[1], "Papa boit dans les pins. Papa peint dans les bois. Dans les bois, papa boit et\n" +
                        "peint. Un pêcheur prépare pitance, plaid, pliant, pipe, parapluie, prend panier\n" +
                        "percé pour ne pas perdre petits poissons, place dans poche petit pot parfaite\n" +
                        "piquette, puis part pédestrement pêcher pendant période permise par police.")
            )
        ),
    )
}
