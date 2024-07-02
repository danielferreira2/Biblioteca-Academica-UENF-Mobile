package com.danielferreira.uenf_educar_biblioteca.ui.navigation

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.internal.composableLambda
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.danielferreira.uenf_educar_biblioteca.network.models.DocumentType
import com.danielferreira.uenf_educar_biblioteca.network.models.ResearchAreaEnum
import com.danielferreira.uenf_educar_biblioteca.ui.components.FilePicker
import com.danielferreira.uenf_educar_biblioteca.ui.screens.CreateDocumentScreen
import com.danielferreira.uenf_educar_biblioteca.ui.screens.DocumentScreen
import com.danielferreira.uenf_educar_biblioteca.ui.screens.DropdownMenuField
import com.danielferreira.uenf_educar_biblioteca.ui.screens.EditScreen
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
                sideNavigationDrawerViewModel = sideNavigationDrawerViewModel,
                documentViewModel = documentViewModel
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
        composable("create_document"){
            CreateDocumentScreen(
                onDismiss = { navController.popBackStack() },
                onSave ={

                } ,
                documentViewModel = documentViewModel
            )
        }

        composable(Constants.EDIT_DOCUMENT){
            val documentId = it.arguments?.getString("id")

            EditScreen(documentId.toString(),documentViewModel, LocalContext.current)
        }
    }
}


object Constants {
    const val DOCUMENT_ID = "document/{id}"
    const val EDIT_DOCUMENT = "edit_document/{id}"
}
