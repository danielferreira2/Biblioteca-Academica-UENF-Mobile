package com.danielferreira.uenf_educar_biblioteca.ui.screens

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.danielferreira.uenf_educar_biblioteca.network.models.Document
import com.danielferreira.uenf_educar_biblioteca.viewmodels.DocumentViewModel


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DocumentScreen(
    navController: NavController,
    documentViewModel: DocumentViewModel,
    documentId: String?
) {

    val documentList by documentViewModel.itemsStateFlow.collectAsState()
    val isRefreshing by documentViewModel.isRefreshing.collectAsState()
    val isLoading by documentViewModel.isItemsLoading.collectAsState()
    val context = LocalContext.current

    val code = documentId.orEmpty().replace("{", "").replace("}", "")

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { refreshDocuments(documentViewModel, code) }
    )

    LaunchedEffect(Unit) {
        loadDocuments(documentViewModel, code, context)
    }

    if (isLoading) {
        LoadingScreen()
    } else {
        DocumentListScreen(documentList, pullRefreshState, isRefreshing)
    }
}

@Composable
fun LoadingScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DocumentListScreen(documentList: List<Document>, pullRefreshState: PullRefreshState, isRefreshing: Boolean) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        if (documentList.isEmpty()) {
            EmptyDocumentListScreen(pullRefreshState)
        }
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(documentList) { item ->
                DocumentListItem(document = item, onClick = {
                    //açao clique
                })
            }
        }
        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EmptyDocumentListScreen(pullRefreshState:PullRefreshState) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Nenhum documento")
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DocumentListItem(document: Document, onClick:() -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 10.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            DocumentDetails(
                document = document,
                modifier = Modifier
                    .padding(0.4.dp)
                    .weight(0.8f))
            DownloadButton(onClick)

        }
    }
}

@Composable
fun DocumentDetails(document: Document,modifier: Modifier) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = document.title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
        Divider()
        DocumentInfoRow("Tipo: ", document.type, "Idioma: ", document.language)
        DocumentInfoRow("Data: ", document.date)
        Text(
            text = "Palavras-Chave: ",
            fontWeight = FontWeight.Bold
        )
        KeywordRow(document.keywords)
    }
}

@Composable
fun DocumentInfoRow(label1: String, value1: String, label2: String? = null, value2: String? = null) {
    Row {
        Text(
            text = label1,
            fontWeight = FontWeight.Bold
        )
        Text(text = value1)
        if (label2 != null && value2 != null) {
            Spacer(modifier = Modifier.size(20.dp))
            Text(
                text = label2,
                fontWeight = FontWeight.Bold
            )
            Text(text = value2)
        }
    }
}

@Composable
fun KeywordRow(keywords: List<String>) {
    Row {
        keywords.take(3).forEach { keyword ->
            Spacer(modifier = Modifier.size(10.dp))
            Text(text = keyword)
        }
    }
}

@Composable
fun DownloadButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Default.Download,
            contentDescription = null
        )
    }
}

private fun refreshDocuments(documentViewModel: DocumentViewModel, code: String) {
    documentViewModel.refreshing()
    if (code == "Geral") {
        documentViewModel.getAllDocuments(
            onSuccess = { documentViewModel.refresh() },
            onError = { documentViewModel.refresh() }
        )
    } else {
        documentViewModel.getFilteredDocumentByResearchArea(
            onSuccess = { documentViewModel.refresh() },
            onError = { documentViewModel.refresh() },
            code
        )
    }
}

private fun loadDocuments(documentViewModel: DocumentViewModel, code: String, context: Context) {
    documentViewModel.itemsLoading()


    if (code == "Geral") {
        documentViewModel.getAllDocuments(
            onSuccess = { documentViewModel.itemsLoaded() },
            onError =  {
                documentViewModel.itemsLoaded()
                Toast.makeText(context, "Erro: $it", Toast.LENGTH_SHORT).show()
            }
        )
    } else {
        documentViewModel.getFilteredDocumentByResearchArea(
            onSuccess = { documentViewModel.itemsLoaded() },
            onError = {
                documentViewModel.itemsLoaded()
                Toast.makeText(context, "Erro: $it", Toast.LENGTH_SHORT).show()
            },
            code
        )
    }
}
