package com.danielferreira.uenf_educar_biblioteca.network.models


data class Document (
    val _id: String,
    val title: String,
    val description: String,
    val type: String,
    val file: String,
    val researchArea: String,
    val library: String,
    val date: String,
    val language: String,
    val authors: List<String>,
    val keywords: List<String>,
    val createdAt: String,
    val updatedAt: String
)

data class DocumentRequest(
    val page: Int = 1,
    val perPage: Int = 20,
    val dateStart: String? = null,
    val dateEnd: String? = null,
    val type: String? = null,
    val title: String? = null,
    val researchArea: String? = null,
    val library: String? = null,
    val language: String? = null,
    val authors: String? = null,
    val keywords: String? = null
)

data class DocumentUpdateRequest(
    val title: String?,
    val description: String?,
    val type: String?,
    val file: String?,
    val researchArea: String?,
    val library: String?,
    val date: String?,
    val language: String?,
    val authors: List<String>?,
    val keywords: List<String>?
)

data class DocumentUpdateResponse(
    val message: String,
    val document: Document
)

data class DocumentListResponse(
    val documents: List<Document>,
    val totalSize: String,
    val message: String
)

data class UploadResponse(
    val message: String,
    val pathName: String
)

data class CreateDocumentRequest(
    val title: String,
    val description: String,
    val type: String,
    val file: String,
    val researchArea: String,
    val library: String,
    val date: String,
    val language: String,
    val authors: List<String>,
    val keywords: List<String>
)

data class CreateDocumentResponse(
    val message: String
)


enum class DocumentType(name:String) {
    ARTIGO("Artigo"),
    MONOGRAFIA("Monografia"),
    TESE("Tese"),
    LIVRO("Livro"),
    DISSERTACOES("Dissertacoes")
}

enum class ResearchAreaEnum(name:String) {
    COMPUTACAO("Computação"),
    MATEMATICA("Matematica"),
    FISICA("Fisica"),
    QUIMICA("Quimica"),
    BIOLOGIA("Biologia"),
    FILOSOFIA("Filosofia"),
    AGRONOMIA("Agronomia"),
    SOCIOLOGIA("Sociologia"),
    ZOOTECNIA("Zootecnia"),
    GERAL("Geral")
}




data class DownloadResponse(val filePath: String)
data class DeleteResponse(val id: String,val message: String)
data class LanguagesResponse(val languages: List<String>)
data class TypesResponse(val types: List<String>)