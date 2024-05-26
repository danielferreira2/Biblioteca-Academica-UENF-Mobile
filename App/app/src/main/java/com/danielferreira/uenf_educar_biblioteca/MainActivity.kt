package com.danielferreira.uenf_educar_biblioteca

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.danielferreira.uenf_educar_biblioteca.ui.components.AppBar
import com.danielferreira.uenf_educar_biblioteca.ui.components.DrawerBody
import com.danielferreira.uenf_educar_biblioteca.ui.components.DrawerHeader
import com.danielferreira.uenf_educar_biblioteca.ui.theme.AppTheme
import com.danielferreira.uenf_educar_biblioteca.viewmodels.LoginViewModel
import com.danielferreira.uenf_educar_biblioteca.viewmodels.SideNavigationDrawerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    private val sideNavigationDrawerViewModel: SideNavigationDrawerViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme(sideNavigationDrawerViewModel = sideNavigationDrawerViewModel) {
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()
                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        ModalDrawerSheet {
                            DrawerHeader(sideNavigationDrawerViewModel)
                            DrawerBody(loginViewModel)
                        }
                    },
                ) {
                    Scaffold(
                        topBar = {
                            AppBar(onNavigationIconClick = {
                                scope.launch {
                                    drawerState.apply {
                                        if (isClosed) open() else close()
                                    }
                                }
                            })
                        }
                    ) {

                        Column(
                            modifier = Modifier.padding(it)
                        ) {

                        }
                    }
                }
            }
        }
    }
}

