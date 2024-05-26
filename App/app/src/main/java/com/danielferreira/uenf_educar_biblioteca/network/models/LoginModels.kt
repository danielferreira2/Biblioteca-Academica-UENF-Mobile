package com.danielferreira.uenf_educar_biblioteca.network.models

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val user: User,
    val token: String
)

data class User(
    val _id: String,
    val name: String,
    val email: String
)
