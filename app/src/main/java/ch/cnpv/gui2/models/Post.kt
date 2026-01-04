package ch.cnpv.gui2.models

import com.google.gson.annotations.SerializedName

data class Post(
    val id: String,
    val description: String,
    @SerializedName("image_id")
    val imageId: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
)