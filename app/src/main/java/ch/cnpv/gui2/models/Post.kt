package ch.cnpv.gui2.models

data class Post(
    val id: Int,
    val profil: Profil,
    val description: String,
    val topic : String,
    val image: Int,
    val comments: List<Comment>
    ){}