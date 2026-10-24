package com.example.gestorbaos.ui.theme.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gestorbaos.model.Componente
import com.example.gestorbaos.model.Receta

@Composable
fun DetalleScreen(receta: Receta) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = "Receta ID: ${receta.codigo}", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                Text(text = receta.nombre, style = MaterialTheme.typography.titleMedium, color = Color.Gray)
            }
            AssistChip(onClick = { }, label = { Text(receta.maquina) })
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Parámetros del Baño", fontWeight = FontWeight.Bold)

        Column(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
            Row(modifier = Modifier.fillMaxWidth().background(Color.LightGray).padding(8.dp)) {
                Text("Componente", modifier = Modifier.weight(1.4f), fontWeight = FontWeight.Bold)
                Text("Cantidad.", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
                Text("A MANO", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
            }

            receta.componentes.forEach { comp ->
                Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                    Text(comp.nombre, modifier = Modifier.weight(1.4f))
                    Text(comp.cant, modifier = Modifier.weight(1f))
                    Text(comp.mano, modifier = Modifier.weight(1f))
                }
                HorizontalDivider()
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
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
            )
        )
    }
}

