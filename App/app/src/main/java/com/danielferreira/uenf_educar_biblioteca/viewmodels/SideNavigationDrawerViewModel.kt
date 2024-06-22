package com.danielferreira.uenf_educar_biblioteca.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SideNavigationDrawerViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _isDarkMode = MutableStateFlow(getInitialTheme())
    val isDarkMode: StateFlow<Boolean> = _isDarkMode

    private val _topBarTitle = MutableStateFlow("areas de pesquisa")
    val topBarTitle: StateFlow<String> = _topBarTitle
    private fun getInitialTheme(): Boolean {
        return sharedPreferences.getBoolean("dark_mode", false)
    }

    fun toggleTheme() {
        _isDarkMode.value = !_isDarkMode.value
        saveTheme(_isDarkMode.value)
    }

    private fun saveTheme(isDarkMode: Boolean) {
        viewModelScope.launch {
            sharedPreferences.edit().putBoolean("dark_mode", isDarkMode).apply()
        }
    }
}