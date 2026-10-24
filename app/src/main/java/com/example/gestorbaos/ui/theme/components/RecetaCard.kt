package com.example.gestorbaos.ui.theme.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gestorbaos.model.Maquina
import com.example.gestorbaos.model.Receta

@Composable
fun RecetaCard(
    receta: Receta,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ){
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "${receta.codigo}- ${receta.nombre}",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Máquina: ${receta.maquina} · ${receta.componentes.size} Componentes",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RecetaCardPreview (){
    RecetaCard(
        modifier = Modifier,
        receta = Receta(
            nombre = "TOB ALGODON",
            codigo = 15,
            maquina = Maquina.SANFOR
        )
    )
}