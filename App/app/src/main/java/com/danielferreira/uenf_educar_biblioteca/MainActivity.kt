package com.danielferreira.uenf_educar_biblioteca

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.navigation.compose.rememberNavController
import com.danielferreira.uenf_educar_biblioteca.ui.navigation.AppNavigation
import com.danielferreira.uenf_educar_biblioteca.ui.theme.AppTheme
import com.danielferreira.uenf_educar_biblioteca.viewmodels.DocumentViewModel
import com.danielferreira.uenf_educar_biblioteca.viewmodels.LoginViewModel
import com.danielferreira.uenf_educar_biblioteca.viewmodels.SideNavigationDrawerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    private val sideNavigationDrawerViewModel: SideNavigationDrawerViewModel by viewModels()
    private val documentViewModel: DocumentViewModel by viewModels()
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme(sideNavigationDrawerViewModel = sideNavigationDrawerViewModel) {
                val navController = rememberNavController()

                AppNavigation(
                    navController = navController,
                    loginViewModel = loginViewModel,
                    documentViewModel =documentViewModel,
                    sideNavigationDrawerViewModel = sideNavigationDrawerViewModel
                )
            }
        }
    }
}

