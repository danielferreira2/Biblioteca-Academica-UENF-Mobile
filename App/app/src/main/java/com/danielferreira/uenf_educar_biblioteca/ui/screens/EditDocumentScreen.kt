package com.danielferreira.uenf_educar_biblioteca.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.danielferreira.uenf_educar_biblioteca.network.models.DocumentType
import com.danielferreira.uenf_educar_biblioteca.network.models.DocumentUpdateRequest
import com.danielferreira.uenf_educar_biblioteca.network.models.ResearchAreaEnum
import com.danielferreira.uenf_educar_biblioteca.viewmodels.DocumentViewModel

@Composable
fun EditScreen(documentId: String, documentViewModel: DocumentViewModel, context: Context) {
    val document = documentViewModel.itemsStateFlow.collectAsState()
        .value.find { it._id == documentId }

    Surface(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.End
        ) {
            OutlinedTextField(
                value = documentViewModel.title,
                onValueChange = { documentViewModel.title = it },
                label = { Text("Titulo") },
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
                label = "Area de Pesquisa",
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
                label = "Language",
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
                label = { Text("Palavras-Chaves") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            Button(
                onClick = {
                    documentViewModel.updateDocument(
                        document!!._id,
                        DocumentUpdateRequest(
                            title = documentViewModel.title,
                            description = documentViewModel.description,
                            type = documentViewModel.type,
                            researchArea = documentViewModel.researchArea,
                            language = documentViewModel.language,
                            authors = documentViewModel.authors.split(","),
                            keywords = documentViewModel.keywords.split(","),
                            file = document.file,
                            date = document.date,
                            library = document.library,
                        ),
                        onError = { Toast.makeText(context, it, Toast.LENGTH_SHORT).show() },
                        onSuccess = { Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show() }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "Atualizar documento")
            }
        }
    }
}
