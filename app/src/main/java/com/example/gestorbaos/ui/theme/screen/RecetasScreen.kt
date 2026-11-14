package com.example.gestorbaos.ui.theme.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    var maquinaFiltro by remember { mutableStateOf("Todas") }

    val recetasFiltradas = recetas.filter { receta ->
        maquinaFiltro == "Todas" || receta.maquina == maquinaFiltro
    }
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Buscar...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val textStyle = MaterialTheme.typography.labelMedium

            FilterChip(
                selected = maquinaFiltro == "Todas",
                onClick = { maquinaFiltro = "Todas" },
                label = { Text("Todas", style = textStyle) },
                border = FilterChipDefaults.filterChipBorder(enabled = true, selected = maquinaFiltro == "Todas")
            )
            FilterChip(
                selected = maquinaFiltro == "SANFOR",
                onClick = { maquinaFiltro = "SANFOR" },
                label = { Text("SANFOR", style = textStyle) },
                border = FilterChipDefaults.filterChipBorder(enabled = true, selected = maquinaFiltro == "SANFOR")
            )
            FilterChip(
                selected = maquinaFiltro == "BRUGMAN 1",
                onClick = { maquinaFiltro = "BRUGMAN 1" },
                label = { Text("BRUGMAN 1", style = textStyle) },
                border = FilterChipDefaults.filterChipBorder(enabled = true, selected = maquinaFiltro == "BRUGMAN 1")
            )
            FilterChip(
                selected = maquinaFiltro == "OSTHOFF",
                onClick = { maquinaFiltro = "OSTHOFF" },
                label = { Text("OSTHOFF", style = textStyle) },
                border = FilterChipDefaults.filterChipBorder(enabled = true, selected = maquinaFiltro == "OSTHOFF")
            )
        }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(recetasFiltradas) { receta ->
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