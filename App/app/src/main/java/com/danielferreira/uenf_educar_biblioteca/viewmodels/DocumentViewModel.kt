package com.danielferreira.uenf_educar_biblioteca.viewmodels

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danielferreira.uenf_educar_biblioteca.network.api_services.DocumentService
import com.danielferreira.uenf_educar_biblioteca.network.models.CreateDocumentRequest
import com.danielferreira.uenf_educar_biblioteca.network.models.CreateDocumentResponse
import com.danielferreira.uenf_educar_biblioteca.network.models.DeleteResponse
import com.danielferreira.uenf_educar_biblioteca.network.models.Document
import com.danielferreira.uenf_educar_biblioteca.network.models.DocumentUpdateRequest
import com.danielferreira.uenf_educar_biblioteca.network.models.DocumentUpdateResponse
import com.danielferreira.uenf_educar_biblioteca.network.models.UploadResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class DocumentViewModel @Inject constructor(
    private val documentService: DocumentService
):ViewModel() {

    private var _itemsStateFlow = MutableStateFlow<List<Document>>(emptyList())
    val itemsStateFlow = _itemsStateFlow.asStateFlow()

    private var _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()



    private var _isRefreshLoading = MutableStateFlow(false)
    val isRefreshLoading = _isRefreshLoading.asStateFlow()

    private var _isItemsLoading = MutableStateFlow(false)
    val isItemsLoading = _isItemsLoading.asStateFlow()

    var title by  mutableStateOf("")
    var description by mutableStateOf("")
    var type by mutableStateOf("")
    var file by mutableStateOf("")
    var researchArea by mutableStateOf("")
    var library by mutableStateOf("663946e35d7526b4e1867e07")
    var date by mutableStateOf("")
    var language by mutableStateOf("")
    var authors by mutableStateOf("")
    var keywords by mutableStateOf("")



    fun itemsLoading() {
        _isItemsLoading.value = true
    }

    fun itemsLoaded() {
        _isItemsLoading.value = false
    }

    fun refreshing() {
        _isRefreshing.value = true
    }

    fun refresh() {
        _isRefreshing.value = false
    }

    fun getAllDocuments(
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                val response = documentService.getAllDocuments()
                if (response.isSuccessful) {
                    val value = response.body()?.documents!!
                    _itemsStateFlow.value = value
                    onSuccess("ok")
                } else {
                    onError("Erro: ${response.code()}")
                }
            } catch (e: Exception) {
                onError(e.message ?: "Erro desconhecido")
            }
        }
    }



    fun getFilteredDocumentByResearchArea(
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit,
        researchAreaID: String
    ) {
        viewModelScope.launch {
            try {
                val response = documentService
                    .filteredDocuments(
                        researchArea = researchAreaID
                    )

                if (response.isSuccessful) {
                    val value = response.body()?.let {
                        _itemsStateFlow.value = it.documents
                        it
                    }
                    onSuccess("ok")
                } else {
                    onError("Erro: ${response.code()}")
                }
            } catch (e: Exception) {
                onError(e.message ?: "Erro desconhecido")
            }
        }
    }

    fun deleteDocument(
        document: Document,
        onSuccess: (DeleteResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = documentService.deleteDocument(document._id, document)
                if (response.isSuccessful) {
                    response.body()?.let {
                        onSuccess(it)
                    } ?: run {
                        onError("Erro ao obter resposta do servidor.")
                    }
                } else {
                    onError("Erro: ${response.code()}")
                }
            } catch (e: Exception) {
                onError(e.message ?: "Erro desconhecido")
            }
        }
    }

    fun updateDocument(
        id: String,
        request: DocumentUpdateRequest,
        onSuccess: (DocumentUpdateResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = documentService.updateDocument(id, request)
                if (response.isSuccessful) {
                    response.body()?.let {
                        onSuccess(it)
                    } ?: run {
                        onError("Erro ao obter resposta do servidor.")
                    }
                } else {
                    onError("Erro: ${response.code()}")
                }
            } catch (e: Exception) {
                onError(e.message ?: "Erro desconhecido")
            }
        }
    }


    fun downloadDocument(
        fileName: String,
        onSuccess: (ResponseBody) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = documentService.downloadDocument(fileName)
                if (response.isSuccessful) {
                    response.body()?.let {
                        onSuccess(it)
                    } ?: run {
                        onError("Erro ao obter resposta do servidor.")
                    }
                } else {
                    onError("Erro: ${response.code()}")
                }
            } catch (e: Exception) {
                onError(e.message ?: "Erro desconhecido")
            }
        }
    }

    val loading = mutableStateOf(false)
    val uploaded = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)

    fun uploadDocument(
        context: Context,
        uri: Uri,
        onSuccess: (UploadResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        //Toast.makeText(context,name,Toast.LENGTH_SHORT).show()
        viewModelScope.launch {
            loading.value = true
            try {
                val multipartBody = createMultipartBody(context, uri, "file")
                val response = documentService.uploadDocument(multipartBody)
                if (response.isSuccessful) {
                    response.body()?.let { res ->
                        onSuccess(res)
                        uploaded.value = true
                        loading.value = false
                    } ?: run {
                        onError("Erro ao obter resposta do servidor.")
                        loading.value = false
                    }
                } else {
                    onError("Erro: ${response.code()}")
                    loading.value = false
                }
            } catch (e: HttpException) {
                if (e.code() == 502) {
                    onError("Erro 502: Bad Gateway. Por favor, tente novamente mais tarde.")
                } else {
                    onError(e.message ?: "Erro desconhecido")
                }
                loading.value = false
            } catch (e: Exception) {
                onError(e.message ?: "Erro desconhecido")
                loading.value = false
            }
        }
    }

    fun createDocument(
        onSuccess: (CreateDocumentResponse) -> Unit,
        onError: (String) -> Unit
    ){
        viewModelScope.launch {
            try {
                val document = CreateDocumentRequest(
                    title = title,
                    description = description,
                    type = "MONOGRAFIA",
                    file = file,
                    researchArea = researchArea,
                    library = library,
                    date = "2024-06-28T02:15:33.000+00:00",
                    language = "pt",
                    authors = authors.split(",").map { it.trim() },
                    keywords = keywords.split(",").map { it.trim() }
                )
                Log.d("DocumentViewModel", document.toString())
                val response = documentService.createDocument(document)
                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.d("DocumentViewModel", it.toString())
                        onSuccess(it)
                    } ?: run {
                        onError("Erro ao obter resposta do servidor.")
                        Log.d("DocumentViewModel", "Erro ao obter resposta do servidor.")
                    }
                } else {
                    onError("Erro: ${response.code()}")
                    Log.d("DocumentViewModel", "Erro: ${response.code()}")
                }
            } catch (e: Exception) {
                onError(e.message ?: "Erro desconhecido")
                Log.d("DocumentViewModel", e.message ?: "Erro desconhecido")
            }
        }

    }



}
fun createMultipartBody(context: Context, uri: Uri, fieldName: String): MultipartBody.Part {
    val contentResolver = context.contentResolver
    val inputStream: InputStream? = contentResolver.openInputStream(uri)
    val name = getFileName(context, uri)
    val requestFile = inputStream?.readBytes()?.let { bytes ->
        RequestBody.create(
            contentResolver.getType(uri)?.toMediaTypeOrNull(),
            bytes
        )
    } ?: throw IllegalArgumentException("Invalid file path")
    return MultipartBody.Part.createFormData(fieldName, name, requestFile)
}

fun getFileName(context: Context, uri: Uri): String? {
    val contentResolver = context.contentResolver
    var fileName: String? = null

    // Verifica se a URI é do tipo Content
    if (uri.scheme == "content") {
        contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                fileName = cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
            }
        }
    }

    // Se o nome não foi encontrado ou a URI não é do tipo Content
    if (fileName == null) {
        fileName = uri.path?.substringAfterLast('/')
    }

    // Substitui espaços por underscores
    fileName = fileName?.replace(" ", "_")

    return fileName
}