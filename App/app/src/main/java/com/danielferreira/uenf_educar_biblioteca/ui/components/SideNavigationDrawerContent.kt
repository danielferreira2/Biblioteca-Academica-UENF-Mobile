package com.danielferreira.uenf_educar_biblioteca.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.danielferreira.uenf_educar_biblioteca.viewmodels.SideNavigationDrawerViewModel

@Composable
fun DrawerHeader(viewModel: SideNavigationDrawerViewModel) {

    val isDarkMode = viewModel.isDarkMode.collectAsState()
    val userName = viewModel.userName.collectAsState()

    Column(
        modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Bem vindo, ${userName.value ?: "visitante"}",
                fontSize = 20.sp
            )

            IconButton(
                onClick = {
                    viewModel.toggleTheme()
                }
            ) {
                Icon(
                    imageVector = if (isDarkMode.value) Icons.Default.LightMode else
                        Icons.Default.DarkMode,
                    contentDescription = "dark mode/light mode button"
                )
            }
        }
    }
}


@Composable
fun DrawerBody(
    onNavigate: (String) -> Unit,
) {
    NavigationDrawerItem(
        label = { Text(text = "Meu perfil") },
        icon = {
            Icon(
                imageVector = Icons.Default.AccountBox,
                contentDescription = null
            )
        },
        selected = false,
        onClick = {
            onNavigate("login")
        }
    )
    NavigationDrawerItem(
        label = { Text(text = "Criar Documento") },
        icon = {
            Icon(
                imageVector = Icons.Default.Create,
                contentDescription = null
            )
        },
        selected = false,
        onClick = {
            onNavigate("create_document")
        }
    )
    NavigationDrawerItem(
        label = { Text(text = "Sobre") },
        icon = {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null
            )
        },
        selected = false,
        onClick = { /*TODO*/ }
    )
    NavigationDrawerItem(
        label = { Text(text = "Sair") },
        icon = {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ExitToApp,
                contentDescription = null
            )
        },
        selected = false,
        onClick = { /*TODO*/ }
    )
}
