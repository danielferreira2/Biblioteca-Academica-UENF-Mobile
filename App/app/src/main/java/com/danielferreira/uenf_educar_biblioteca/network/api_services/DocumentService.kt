package com.danielferreira.uenf_educar_biblioteca.network.api_services

import android.net.Uri
import com.danielferreira.uenf_educar_biblioteca.network.models.CreateDocumentRequest
import com.danielferreira.uenf_educar_biblioteca.network.models.CreateDocumentResponse
import com.danielferreira.uenf_educar_biblioteca.network.models.DeleteResponse
import com.danielferreira.uenf_educar_biblioteca.network.models.Document
import com.danielferreira.uenf_educar_biblioteca.network.models.DocumentListResponse
import com.danielferreira.uenf_educar_biblioteca.network.models.DocumentUpdateRequest
import com.danielferreira.uenf_educar_biblioteca.network.models.DocumentUpdateResponse
import com.danielferreira.uenf_educar_biblioteca.network.models.DownloadResponse
import com.danielferreira.uenf_educar_biblioteca.network.models.UploadResponse
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface DocumentService {

    @GET("documents/")
    suspend fun getAllDocuments():retrofit2.Response<DocumentListResponse>

    @GET("document/{id}/")
    suspend fun getDocumentById(@Path("id") id: String): retrofit2.Response<Document>

    @GET("documents/")
    suspend fun filteredDocuments(
        @Query("page") page: Int = 1,
        @Query("perPage") perPage: Int = 20,
        @Query("dateStart") dateStart: String? = null,
        @Query("dateEnd") dateEnd: String? = null,
        @Query("type") type: String? = null,
        @Query("title") title: String? = null,
        @Query("researchArea") researchArea: String? = null,
        @Query("library") library: String? = null,
        @Query("language") language: String? = null,
        @Query("authors") authors: String? = null,
        @Query("keywords") keywords: String? = null
    ): retrofit2.Response<DocumentListResponse>

    @HTTP(method = "DELETE", path = "document/{id}/", hasBody = true)
    suspend fun deleteDocument(
        @Path("id") id: String,
        @Body request: Document
    ):retrofit2.Response<DeleteResponse>

    @GET("document/download/{id}/")
    suspend fun downloadDocument(@Path("id") fileName: String): retrofit2.Response<ResponseBody>

    @Multipart
    @POST("document/upload/")
    suspend fun uploadDocument(
        @Part file: MultipartBody.Part,
    ): retrofit2.Response<UploadResponse>

    @POST("document/")
    suspend fun createDocument(
        @Body request: CreateDocumentRequest
    ): retrofit2.Response<CreateDocumentResponse>


    @PUT("document/{id}/")
    suspend fun updateDocument(
        @Path("id") id: String,
        @Body request: DocumentUpdateRequest
    ):retrofit2.Response<DocumentUpdateResponse>

}