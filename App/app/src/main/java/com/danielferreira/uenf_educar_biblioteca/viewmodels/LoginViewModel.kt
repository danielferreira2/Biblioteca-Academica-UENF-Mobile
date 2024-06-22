package com.danielferreira.uenf_educar_biblioteca.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danielferreira.uenf_educar_biblioteca.network.api_services.LoginService
import com.danielferreira.uenf_educar_biblioteca.network.models.LoginRequest
import com.danielferreira.uenf_educar_biblioteca.network.models.LoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginService: LoginService,
    private val sharedPreferences: SharedPreferences,
): ViewModel()
{
    fun login(
        email: String,
        password: String,
        onSuccess: (LoginResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = loginService.login(LoginRequest(email, password))
                if (response.isSuccessful) {
                    response.body()?.let { loginResponse ->
                        // Salvar token no SharedPreferences
                        sharedPreferences.edit()
                            .putString("jwt_token", loginResponse.token).apply()
                        onSuccess(loginResponse)
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