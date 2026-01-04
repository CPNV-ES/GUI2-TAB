package ch.cnpv.gui2.network

import ch.cnpv.gui2.models.Comment
import ch.cnpv.gui2.models.Post
import ch.cnpv.gui2.models.Profil
import retrofit2.http.*

interface ApiService {

    // Profils
    @GET("profils")
    suspend fun getProfils(): List<Profil>

    @GET("profils/{id}")
    suspend fun getProfil(@Path("id") id: String): Profil

    // Friends
    @GET("profils/{id}/friends")
    suspend fun getFriends(@Path("id") id: String): List<Profil>

    // Posts
    @GET("profils/{id}/posts")
    suspend fun getPosts(@Path("id") id: String): List<Post>

    @GET("profils/{id}/posts/{post_id}")
    suspend fun getPost(
        @Path("id") id: String,
        @Path("post_id") postId: String
    ): Post

    // Comments
    @GET("posts/{id}/comments")
    suspend fun getComments(@Path("id") id: String): List<Comment>
}