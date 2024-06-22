package com.danielferreira.uenf_educar_biblioteca.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.danielferreira.uenf_educar_biblioteca.ui.screens.DocumentScreen
import com.danielferreira.uenf_educar_biblioteca.ui.screens.HomeScreen
import com.danielferreira.uenf_educar_biblioteca.ui.screens.LoginScreen
import com.danielferreira.uenf_educar_biblioteca.viewmodels.DocumentViewModel
import com.danielferreira.uenf_educar_biblioteca.viewmodels.LoginViewModel
import com.danielferreira.uenf_educar_biblioteca.viewmodels.SideNavigationDrawerViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    documentViewModel: DocumentViewModel,
    sideNavigationDrawerViewModel: SideNavigationDrawerViewModel
) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                mainNavController = navController,
                sideNavigationDrawerViewModel = sideNavigationDrawerViewModel
            )
        }
        composable("login") {
            LoginScreen(
                navController = navController,
                loginViewModel = loginViewModel
            )
        }
        composable(Constants.DOCUMENT_ID) {
            val documentId = it.arguments?.getString("id")
            DocumentScreen(
                navController = navController,
                documentViewModel = documentViewModel,
                documentId = documentId
            )
        }
    }
}

object Constants {
    const val DOCUMENT_ID = "document/{id}"
}
