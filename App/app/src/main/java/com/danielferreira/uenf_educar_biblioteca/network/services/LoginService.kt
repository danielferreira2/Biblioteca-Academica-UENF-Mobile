package com.danielferreira.uenf_educar_biblioteca.network.services

import com.danielferreira.uenf_educar_biblioteca.network.models.LoginRequest
import com.danielferreira.uenf_educar_biblioteca.network.models.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("/login")
    suspend fun login(@Body request: LoginRequest): retrofit2.Response<LoginResponse>

}