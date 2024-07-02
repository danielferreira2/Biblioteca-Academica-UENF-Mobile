package com.danielferreira.uenf_educar_biblioteca.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.danielferreira.uenf_educar_biblioteca.network.models.DocumentType
import com.danielferreira.uenf_educar_biblioteca.network.models.ResearchAreaEnum
import com.danielferreira.uenf_educar_biblioteca.ui.components.FilePicker
import com.danielferreira.uenf_educar_biblioteca.viewmodels.DocumentViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateDocumentScreen(
    onDismiss: () -> Unit,
    onSave: (DocumentInfo) -> Unit,
    documentViewModel: DocumentViewModel,
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Surface(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Crie um novo documento",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Preencha as informações abaixo para criar um novo documento na biblioteca.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = documentViewModel.title,
                onValueChange = { documentViewModel.title = it },
                label = { Text("Título") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
            OutlinedTextField(
                value = documentViewModel.description,
                onValueChange = { documentViewModel.description = it },
                label = { Text("Descrição") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
            DropdownMenuField(
                value = documentViewModel.type,
                onValueChange = { documentViewModel.type = it },
                label = "Tipo de Documento",
                options = listOf(
                    DocumentType.TESE.name,
                    DocumentType.ARTIGO.name,
                    DocumentType.LIVRO.name,
                    DocumentType.MONOGRAFIA.name,
                    DocumentType.DISSERTACOES.name
                )
            )
            DropdownMenuField(
                value = documentViewModel.researchArea,
                onValueChange = { documentViewModel.researchArea = it },
                label = "Área de Pesquisa",
                options = listOf(
                    ResearchAreaEnum.GERAL.name,
                    ResearchAreaEnum.COMPUTACAO.name,
                    ResearchAreaEnum.MATEMATICA.name,
                    ResearchAreaEnum.FISICA.name,
                    ResearchAreaEnum.QUIMICA.name,
                    ResearchAreaEnum.BIOLOGIA.name,
                    ResearchAreaEnum.FILOSOFIA.name,
                    ResearchAreaEnum.AGRONOMIA.name,
                    ResearchAreaEnum.SOCIOLOGIA.name,
                    ResearchAreaEnum.ZOOTECNIA.name
                )
            )
            DropdownMenuField(
                value = documentViewModel.language,
                onValueChange = { documentViewModel.language = it },
                label = "Idioma",
                options = listOf("pt", "en", "es")
            )
            OutlinedTextField(
                value = documentViewModel.authors,
                onValueChange = { documentViewModel.authors = it },
                label = { Text("Autores") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
            OutlinedTextField(
                value = documentViewModel.keywords,
                onValueChange = { documentViewModel.keywords = it },
                label = { Text("Palavras-chave") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
            Row {
                if (documentViewModel.loading.value) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.size(10.dp))
                }

                FilePicker { uri ->
                    uri?.let {
                        documentViewModel.uploadDocument(context, uri,
                            onSuccess = {
                                Toast.makeText(context, it.pathName, Toast.LENGTH_SHORT).show()
                                documentViewModel.file = it.pathName
                            },
                            onError = {
                                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                }
            }

            if (!documentViewModel.loading.value) {
                Text(text = documentViewModel.file)
            }

            Button(
                onClick = {
                    if (documentViewModel.title.isBlank() ||
                        documentViewModel.description.isBlank() ||
                        documentViewModel.type.isBlank() ||
                        documentViewModel.researchArea.isBlank() ||
                        documentViewModel.language.isBlank() ||
                        documentViewModel.authors.isBlank() ||
                        documentViewModel.keywords.isBlank() ||
                        documentViewModel.file.isBlank()) {
                        Toast.makeText(context, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                    } else {
                        documentViewModel.createDocument(
                            onSuccess = {
                                Toast.makeText(context, "Documento criado com sucesso!", Toast.LENGTH_SHORT).show()
                                onDismiss()
                            },
                            onError = {
                                Toast.makeText(context, "Erro ao criar documento", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Criar documento")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    options: List<String>
) {
    var expanded by remember { mutableStateOf(false) }
    val textFieldValue = remember { mutableStateOf(value) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = textFieldValue.value,
            onValueChange = {
                textFieldValue.value = it
                onValueChange(it)
            },
            label = { Text(label) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        textFieldValue.value = option
                        onValueChange(option)
                        expanded = false
                    }
                )
            }
        }
    }
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
