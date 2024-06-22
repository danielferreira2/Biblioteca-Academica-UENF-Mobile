package com.danielferreira.uenf_educar_biblioteca.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun DocumentCreationDialog(
    onDismiss: () -> Unit,
    onSave: (DocumentInfo) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var file by remember { mutableStateOf("") }
    var researchArea by remember { mutableStateOf("") }
    var library by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var language by remember { mutableStateOf("") }
    var authors by remember { mutableStateOf("") }
    var keywords by remember { mutableStateOf("") }

    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Criar Novo Documento")
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Título") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Descrição") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = type,
                    onValueChange = { type = it },
                    label = { Text("Tipo") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = file,
                    onValueChange = { file = it },
                    label = { Text("Arquivo") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))


                var expanded by remember { mutableStateOf(false) }
                val researchAreas = listOf("Computação", "Matemática", "Física", "Química", "Biologia")
                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = researchArea,
                        onValueChange = { researchArea = it },
                        label = { Text("Área de Pesquisa") },
                        modifier = Modifier.fillMaxWidth(),
                        readOnly = true,
                        trailingIcon = {
                            IconButton(onClick = { expanded = true }) {
                                Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                            }
                        }
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        researchAreas.forEach { area ->
                            DropdownMenuItem(
                                text = { Text(area) },
                                onClick = {
                                    researchArea = area
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = library,
                    onValueChange = { library = it },
                    label = { Text("Biblioteca") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = date,
                    onValueChange = { date = it },
                    label = { Text("Data") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = language,
                    onValueChange = { language = it },
                    label = { Text("Idioma") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = authors,
                    onValueChange = { authors = it },
                    label = { Text("Autores") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = keywords,
                    onValueChange = { keywords = it },
                    label = { Text("Palavras-chave") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isNotBlank() && description.isNotBlank() && type.isNotBlank() && file.isNotBlank() && researchArea.isNotBlank() && library.isNotBlank() && date.isNotBlank() && language.isNotBlank() && authors.isNotBlank() && keywords.isNotBlank()) {
                        onSave(
                            DocumentInfo(
                                title = title,
                                description = description,
                                type = type,
                                file = file,
                                researchArea = researchArea,
                                library = library,
                                date = date,
                                language = language,
                                authors = authors,
                                keywords = keywords
                            )
                        )
                        Toast.makeText(context, "Documento salvo com sucesso", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, "Preencha todos os campos", Toast.LENGTH_LONG).show()
                    }
                }
            ) {
                Text("Salvar")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

data class DocumentInfo(
    val title: String,
    val description: String,
    val type: String,
    val file: String,
    val researchArea: String,
    val library: String,
    val date: String,
    val language: String,
    val authors: String,
    val keywords: String
)
