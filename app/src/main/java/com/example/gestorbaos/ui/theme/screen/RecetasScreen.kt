package com.example.gestorbaos.ui.theme.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gestorbaos.model.Receta
// IMPORTANTE: Asegúrate de importar el componente que acabas de modificar
import com.example.gestorbaos.ui.theme.components.RecetaCard

@Composable
fun RecetasScreen(
    recetas: List<Receta>,
    onRecetaClick: (Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Buscar...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
        )

        Row(modifier = Modifier.padding(vertical = 8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterChip(selected = true, onClick = { }, label = { Text("Todas") })
            FilterChip(selected = false, onClick = { }, label = { Text("Por Máquina") })
            FilterChip(selected = false, onClick = { }, label = { Text("Por Tipo") })
        }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(recetas) { receta ->
                RecetaCard(
                    receta = receta,
                    modifier = Modifier.clickable { onRecetaClick(receta.codigo) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecetasScreenPreview() {
    val mockRecetas = listOf(
        Receta(
            codigo = 15,
            nombre = "TOB ALGODON",
            maquina = com.example.gestorbaos.model.Maquina.SANFOR
        ),
        Receta(
            codigo = 67,
            nombre = "BLANQUEO+MERCERIZADO",
            maquina = com.example.gestorbaos.model.Maquina.BRUGMAN
        ),
        Receta(
            codigo = 27944,
            nombre = "IMPREG. ALGODÓN DIFÍCIL",
            maquina = com.example.gestorbaos.model.Maquina.OSTHOFF
        )
    )
    MaterialTheme {
        RecetasScreen(
            recetas = mockRecetas,
            onRecetaClick = {}
        )
    }
}