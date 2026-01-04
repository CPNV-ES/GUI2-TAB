package ch.cnpv.gui2.models

import com.google.gson.annotations.SerializedName

data class Profil(
    val id: String,
    val name: String,
    @SerializedName("image_id")
    val imageId: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
)