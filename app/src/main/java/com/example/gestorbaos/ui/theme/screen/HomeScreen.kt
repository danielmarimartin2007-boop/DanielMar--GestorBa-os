package com.example.gestorbaos.ui.theme.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gestorbaos.model.Receta
import com.example.gestorbaos.model.Maquina
import com.example.gestorbaos.ui.theme.components.RecetaCard

@Composable
fun HomeScreen(
    recetasFavoritas: List<Receta>,
    onRecetaClick: (Int) -> Unit,
    onNuevaRecetaClick: () -> Unit,
    onAjusteClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {

        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            OutlinedTextField(
                value = "",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Buscar ...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                shape = MaterialTheme.shapes.medium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onAjusteClick,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text("COMENZAR AJUSTE DE BAÑO", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Rounded.Star, contentDescription = null, tint = Color(0xFFFFC107))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Recetas Favoritas", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn {
                items(recetasFavoritas) { receta ->
                    RecetaCard(
                        receta = receta,
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                            .clickable { onRecetaClick(receta.codigo) }
                    )
                }
            }
        }

        FloatingActionButton(
            onClick = onNuevaRecetaClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ) {
            Icon(Icons.Default.Add, contentDescription = "Crear nueva receta")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val mockRecetas = listOf(
        Receta(codigo = 15, nombre = "TOB ALGODON", maquina = Maquina.SANFOR),
        Receta(codigo = 67, nombre = "BLANQUEO+MERCERIZADO", maquina = Maquina.BRUGMAN)
    )
    MaterialTheme {
        HomeScreen(
            recetasFavoritas = mockRecetas,
            onRecetaClick = {},
            onNuevaRecetaClick = {},
            onAjusteClick = {}
        )
    }
}
