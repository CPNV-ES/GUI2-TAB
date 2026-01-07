package ch.cnpv.gui2.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Profil(
    val id: String,
    val name: String,
    @SerializedName("image_url")
    val imageUrl: String,
    val hash: String? = null,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
)