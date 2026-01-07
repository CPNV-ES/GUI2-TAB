package ch.cnpv.gui2.network

import ch.cnpv.gui2.models.Comment
import ch.cnpv.gui2.models.Post
import ch.cnpv.gui2.models.Profil
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {

    @GET("profils")
    suspend fun getProfils(): List<Profil>

    @GET("profils/{id}")
    suspend fun getProfil(@Path("id") id: String): Profil

    @GET("profils/{id}/friends")
    suspend fun getFriends(@Path("id") id: String): List<Profil>

    @GET("profils/{id}/posts")
    suspend fun getPosts(@Path("id") id: String): List<Post>

    @GET("profils/{id}/posts/{post_id}")
    suspend fun getPost(
        @Path("id") id: String,
        @Path("post_id") postId: String
    ): Post

    @DELETE("profils/{id}/posts/{post_id}")
    suspend fun deletePost(
        @Path("id") profilId: String,
        @Path("post_id") postId: String
    )

    @GET("posts/{id}/comments")
    suspend fun getComments(@Path("id") id: String): List<Comment>

    @POST("profils/{id}/posts")
    suspend fun postPost(@Path("id") id: String): Post

    @Multipart
    @POST("profils/{id}/posts")
    suspend fun createPostWithImage(
        @Path("id") profilId: String,
        @Part("description") description: RequestBody,
        @Part image: MultipartBody.Part
    ): Post
}