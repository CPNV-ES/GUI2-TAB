package ch.cnpv.gui2.models

import com.google.gson.annotations.SerializedName

data class Comment(
    val id: String,
    @SerializedName("from_id")
    val fromId: String,
    val text: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
)