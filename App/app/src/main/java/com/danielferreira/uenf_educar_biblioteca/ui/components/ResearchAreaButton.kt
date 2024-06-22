package com.danielferreira.uenf_educar_biblioteca.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.danielferreira.uenf_educar_biblioteca.ui.navigation.Constants
import com.danielferreira.uenf_educar_biblioteca.ui.theme.agroColor
import com.danielferreira.uenf_educar_biblioteca.ui.theme.bioColor
import com.danielferreira.uenf_educar_biblioteca.ui.theme.compColor
import com.danielferreira.uenf_educar_biblioteca.ui.theme.filColor
import com.danielferreira.uenf_educar_biblioteca.ui.theme.gerColor
import com.danielferreira.uenf_educar_biblioteca.ui.theme.mathColor
import com.danielferreira.uenf_educar_biblioteca.ui.theme.phisicsColor
import com.danielferreira.uenf_educar_biblioteca.ui.theme.quiColor
import com.danielferreira.uenf_educar_biblioteca.ui.theme.socColor
import com.danielferreira.uenf_educar_biblioteca.ui.theme.zooColor

data class ResearchArea(val id: Int, val name: String, val imageId: Int)

@Composable
fun ResearchAreaGrid(list: List<ResearchArea>, navController: NavController) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(horizontal = 10.dp)
    ) {
        items(list) { item ->
            ResearchAreaButton(researchArea = item, navController = navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResearchAreaButton(navController: NavController, researchArea: ResearchArea) {
    val color = when (researchArea.name) {
        "Computação" -> compColor
        "Matemática" -> mathColor
        "Fisica" -> phisicsColor
        "Quimica" -> quiColor
        "Biologia" -> bioColor
        "Filosofia" -> filColor
        "Agronomia" -> agroColor
        "Sociologia" -> socColor
        "Zootecnia" -> zooColor
        "Geral" -> gerColor
        else -> Color.Gray
    }

    Card(
        shape = RoundedCornerShape(30.dp),
        modifier = Modifier
            .width(180.dp)
            .height(144.dp)
            .padding(horizontal = 10.dp),
        colors = CardDefaults.cardColors(
            containerColor = color,
            contentColor = Color.White
        ),
        onClick = {
            navController.navigate(Constants.DOCUMENT_ID.replace("{id}", researchArea.name))
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = researchArea.imageId),
                modifier = Modifier.fillMaxWidth(),
                contentDescription = null
            )
            Text(
                text = researchArea.name,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
