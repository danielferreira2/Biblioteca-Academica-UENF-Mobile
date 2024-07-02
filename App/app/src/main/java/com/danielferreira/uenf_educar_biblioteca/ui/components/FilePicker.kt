package com.danielferreira.uenf_educar_biblioteca.ui.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.danielferreira.uenf_educar_biblioteca.viewmodels.getFileName


@Composable
fun FilePicker(onFileSelected: (Uri)-> Unit) {
    val result = remember {
        mutableStateOf<Uri?>(null)
    }
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                result.value = it
                onFileSelected(it)
            }
        }
    Button(onClick = {
        launcher.launch("application/*")
    }) {
        Text(text = "Selecionar Arquivo")
    }


}