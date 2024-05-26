package com.danielferreira.uenf_educar_biblioteca.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AccountBox
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.danielferreira.uenf_educar_biblioteca.viewmodels.LoginViewModel
import com.danielferreira.uenf_educar_biblioteca.viewmodels.SideNavigationDrawerViewModel


@Composable
fun DrawerHeader(viewModel: SideNavigationDrawerViewModel) {
    val isDarkMode = viewModel.isDarkMode.collectAsState()

    Column(
        modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp, horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                shape = RoundedCornerShape(60.dp),
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "L",
                        fontSize = 30.sp
                    )
                }
            }
            IconButton(
                onClick = {
                    viewModel.toggleTheme()
                }
            ) {
                Icon(
                    imageVector = if (isDarkMode.value) Icons.Default.LightMode else Icons.Default.DarkMode,
                    contentDescription = "dark mode/light mode button"
                )
            }
        }
    }
}

@Composable
fun DrawerBody(
    loginViewModel: LoginViewModel
) {
    NavigationDrawerItem(
        label = { Text(text = "Meu perfil") },
        icon = {
            Icon(
                imageVector = Icons.Default.AccountBox,
                contentDescription =""
            )
        },
        selected = false,
        onClick = {
            loginViewModel.login(
                "daniel@mail.com",
                "123",
                onSuccess = {
                    Log.d("login","sucesso: ${it.token}")
                },
                onError = {
                    Log.d("login","erro: $it")
                }
            )
        }
    )
    NavigationDrawerItem(
        label = { Text(text = "Criar Documento") },
        icon = {
            Icon(
                imageVector = Icons.Default.Create,
                contentDescription =""
            )
        },
        selected = false,
        onClick = { /*TODO*/ }
    )
    NavigationDrawerItem(
        label = { Text(text = "Sobre") },
        icon = {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription =""
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
                contentDescription =""
            )
        },
        selected = false,
        onClick = { /*TODO*/ }
    )




}
