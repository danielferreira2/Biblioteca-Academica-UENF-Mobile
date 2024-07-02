package com.danielferreira.uenf_educar_biblioteca.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.danielferreira.uenf_educar_biblioteca.R
import com.danielferreira.uenf_educar_biblioteca.ui.components.AppBar
import com.danielferreira.uenf_educar_biblioteca.ui.components.DrawerBody
import com.danielferreira.uenf_educar_biblioteca.ui.components.DrawerHeader
import com.danielferreira.uenf_educar_biblioteca.ui.components.ResearchArea
import com.danielferreira.uenf_educar_biblioteca.ui.components.ResearchAreaGrid
import com.danielferreira.uenf_educar_biblioteca.viewmodels.DocumentViewModel
import com.danielferreira.uenf_educar_biblioteca.viewmodels.SideNavigationDrawerViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    mainNavController: NavController,
    sideNavigationDrawerViewModel: SideNavigationDrawerViewModel,
    documentViewModel: DocumentViewModel
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(false) }

    val researchAreas = listOf(
        ResearchArea(1, "Computação", R.drawable.computacao),
        ResearchArea(2, "Matemática", R.drawable.matematica),
        ResearchArea(3, "Fisica", R.drawable.fisica),
        ResearchArea(4, "Quimica", R.drawable.quimica),
        ResearchArea(5, "Biologia", R.drawable.biologia),
        ResearchArea(6, "Filosofia", R.drawable.filosofia),
        ResearchArea(7, "Agronomia", R.drawable.agronomia),
        ResearchArea(8, "Sociologia", R.drawable.sociologia),
        ResearchArea(9, "Zootecnia", R.drawable.zootecnia),
        ResearchArea(10, "Geral", R.drawable.geral)
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerHeader(sideNavigationDrawerViewModel)
                DrawerBody(
                    onNavigate = {
                        mainNavController.navigate(it)
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                AppBar(
                    title = sideNavigationDrawerViewModel.topBarTitle.collectAsState().value,
                    onNavigationIconClick = {
                        scope.launch {
                            if (drawerState.isClosed) drawerState.open() else drawerState.close()
                        }
                    }
                )
            }
        ) {
            Column(
                modifier = Modifier.padding(it)
            ) {
                Spacer(modifier = Modifier.size(10.dp))
                ResearchAreaGrid(list = researchAreas, navController = mainNavController)
            }
        }
    }


}
