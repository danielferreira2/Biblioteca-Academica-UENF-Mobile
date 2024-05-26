package com.danielferreira.uenf_educar_biblioteca.viewmodels

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danielferreira.uenf_educar_biblioteca.network.models.DeleteResponse
import com.danielferreira.uenf_educar_biblioteca.network.models.Document
import com.danielferreira.uenf_educar_biblioteca.network.models.DocumentUpdateRequest
import com.danielferreira.uenf_educar_biblioteca.network.models.DocumentUpdateResponse
import com.danielferreira.uenf_educar_biblioteca.network.services.DocumentService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DocumentViewModel @Inject constructor(
    private val documentService: DocumentService
):ViewModel() {

    private var _itemsStateFlow = MutableStateFlow<List<Document>>(emptyList())
    val itemsStateFlow = _itemsStateFlow.asStateFlow()


    fun getAllDocuments(
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit,
    ){
        viewModelScope.launch {
            try {
                val  response = documentService.getAllDocuments()
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
    ){
        viewModelScope.launch {
            try {
                val  response = documentService
                    .filteredDocuments(
                        researchArea = researchAreaID
                    )

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

    fun deleteDocument(
        document: Document,
        onSuccess: (DeleteResponse) -> Unit,
        onError: (String) -> Unit
    ){
        viewModelScope.launch {
            try {
                val response = documentService.deleteDocument(document._id,document)
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
    ){
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

}