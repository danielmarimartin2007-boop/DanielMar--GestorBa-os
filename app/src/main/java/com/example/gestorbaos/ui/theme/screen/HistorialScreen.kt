package com.example.gestorbaos.ui.theme.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class RegistroHistorial(
    val codigoAjuste: Int,
    val recetaOrigen: String,
    val recetaDestino: String,
    val maquina: String,
    val fecha: String
)

@Composable
fun HistorialScreen(historial: List<RegistroHistorial>) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Historial de Reutilización", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(historial) { registro ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "Ajuste #${registro.codigoAjuste} - Máquina: ${registro.maquina}",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = registro.recetaOrigen,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Medium
                        )

                        Text(
                            text = "+",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(vertical = 1.dp)
                        )

                        Text(
                            text = registro.recetaDestino,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Medium
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Fecha: ${registro.fecha}",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.DarkGray
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HistorialScreenPreview() {
    val historialMock = listOf(
        RegistroHistorial(
            codigoAjuste = 3,
            recetaOrigen = "TOB ALGODON",
            recetaDestino = "SANFOR TOB NUEVO",
            maquina = "SANFOR",
            fecha = "24/10/2026 - 10:30 AM"
        ),
        RegistroHistorial(
            codigoAjuste = 2,
            recetaOrigen = "IMPREG. ALGODÓN DIFÍCIL",
            recetaDestino = "OSTHOFF IMPREG. MEZCLAS",
            maquina = "OSTHOFF",
            fecha = "23/10/2026 - 16:45 PM"
        ),
        RegistroHistorial(
            codigoAjuste = 1,
            recetaOrigen = "BLANQUEO+MERCERIZADO",
            recetaDestino = "LAVADO TINTURA B1",
            maquina = "BRUGMAN 1",
            fecha = "22/10/2026 - 09:15 AM"
        )
    )
    MaterialTheme {
        HistorialScreen(historial = historialMock)
    }
}
