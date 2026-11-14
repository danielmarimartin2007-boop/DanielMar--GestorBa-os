package com.example.gestorbaos.ui.theme.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gestorbaos.model.Componente
import com.example.gestorbaos.model.Receta

@Composable
fun DetalleScreen(
    receta: Receta,
    onEditClick: () -> Unit,
    onIniciarAjusteClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Receta ID: ${receta.codigo}",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = receta.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            /* Añadir Favorito */
            AssistChip(
                onClick = { },
                label = { Text(receta.maquina, fontWeight = FontWeight.Bold) },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    labelColor = Color.Black
                ),
                border = null
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Parámetros del Baño", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)

        Column(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(12.dp)
            ) {
                Text("Componente", modifier = Modifier.weight(1.5f), fontWeight = FontWeight.Bold, color = Color.Black, style = MaterialTheme.typography.bodyLarge)
                Text("Cant.", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold, color = Color.Black, style = MaterialTheme.typography.bodyLarge)
                Text("Mano (T/F)", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold, color = Color.Black, style = MaterialTheme.typography.bodyLarge)
            }

            receta.componentes.forEach { comp ->
                Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 12.dp)) {
                    Text(comp.nombre, modifier = Modifier.weight(1.5f), style = MaterialTheme.typography.bodyMedium)
                    Text(comp.cant, modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodyMedium)
                    Text(comp.mano, modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodyMedium)
                }
                HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Spacer(modifier = Modifier.weight(1f))

        OutlinedButton(
            onClick = onEditClick,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text("EDITAR RECETA", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onIniciarAjusteClick,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text("INICIAR AJUSTE CON ESTA RECETA", fontWeight = FontWeight.Bold)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetalleScreenPreview() {
    MaterialTheme {
        DetalleScreen(
            receta = Receta(
                codigo = 15,
                nombre = "TOB ALGODON",
                maquina = "Sanford",
                componentes = listOf(
                    Componente("TUBINGAL RNJ", "18 cc/l", "false"),
                    Componente("ÁCIDO ACÉTICO 80%", "3 cc/l", "false"),
                    Componente("AUXICOL", "0,7 cc/l", "true")
                )
            ),
            onEditClick = {},
            onIniciarAjusteClick = {}
        )
    }
}

