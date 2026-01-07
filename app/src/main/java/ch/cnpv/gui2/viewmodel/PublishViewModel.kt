package ch.cnpv.gui2.ui.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.cnpv.gui2.models.Post
import ch.cnpv.gui2.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream

data class PublishUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val publishedPost: Post? = null
)

class PublishViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PublishUiState())
    val uiState: StateFlow<PublishUiState> = _uiState.asStateFlow()

    fun publishPost(
        context: Context,
        description: String,
        imageUri: Uri,
        profilId: String
    ) {
        viewModelScope.launch {
            try {
                _uiState.value = PublishUiState(isLoading = true)

                val tempFile = uriToTempFile(context, imageUri)
                val imagePart = createImagePart(tempFile)
                val descriptionPart = description.toRequestBody("text/plain".toMediaTypeOrNull())

                val newPost = RetrofitInstance.api.createPostWithImage(
                    profilId = profilId,
                    description = descriptionPart,
                    image = imagePart
                )

                tempFile.delete()

                _uiState.value = PublishUiState(
                    isLoading = false,
                    publishedPost = newPost
                )

            } catch (e: Exception) {
                _uiState.value = PublishUiState(
                    isLoading = false,
                    errorMessage = "Erreur lors de la publication : ${e.message}"
                )
                e.printStackTrace()
            }
        }
    }

    fun resetState() {
        _uiState.value = PublishUiState()
    }

    private fun uriToTempFile(context: Context, imageUri: Uri): File {
        val inputStream = context.contentResolver.openInputStream(imageUri)
            ?: throw Exception("Impossible de lire l'image")

        val tempFile = File.createTempFile("upload_", ".jpg", context.cacheDir)
        tempFile.deleteOnExit()

        FileOutputStream(tempFile).use { output ->
            inputStream.copyTo(output)
        }
        inputStream.close()

        return tempFile
    }

    private fun createImagePart(file: File): MultipartBody.Part {
        val requestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(
            "image",
            file.name,
            requestBody
        )
    }
}