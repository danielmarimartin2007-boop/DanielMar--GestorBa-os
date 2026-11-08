package com.example.gestorbaos.ui.theme.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gestorbaos.model.Componente
import com.example.gestorbaos.model.Receta

data class AdicionResultado(
    val nombre: String,
    val concentracion: String,
    val cantidad: Double,
    val unidad: String,
    val esMano: Boolean,
    val esAgua: Boolean = false
)

@SuppressLint("DefaultLocale")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AjusteScreen(
    recetas: List<Receta>,
    recetaInicialId: Int? = null,
    onVolverClick: () -> Unit
) {
    var desplegableActualAbierto by remember { mutableStateOf(false) }

    var recetaActualSeleccionada by remember(recetaInicialId, recetas) {
        mutableStateOf(
            recetas.find { it.codigo == recetaInicialId } ?: recetas.firstOrNull()
        )
    }
    var litrosActualTexto by remember { mutableStateOf("100") }

    var desplegableObjetivoAbierto by remember { mutableStateOf(false) }

    val recetasMismaMaquina = remember(recetaActualSeleccionada, recetas) {
        recetas.filter { it.maquina == recetaActualSeleccionada?.maquina }
    }

    var recetaObjetivoSeleccionada by remember(recetaActualSeleccionada, recetasMismaMaquina) {
        mutableStateOf(
            recetasMismaMaquina.getOrNull(1) ?: recetasMismaMaquina.firstOrNull()
        )
    }
    var litrosObjetivoTexto by remember { mutableStateOf("2000") }

    LaunchedEffect(recetaActualSeleccionada) {
        if (recetaObjetivoSeleccionada?.maquina != recetaActualSeleccionada?.maquina) {
            recetaObjetivoSeleccionada = recetasMismaMaquina.firstOrNull { it.codigo != recetaActualSeleccionada?.codigo }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // 1. RECETA ACTUAL
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "1. ACTUAL",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )

                ExposedDropdownMenuBox(
                    expanded = desplegableActualAbierto,
                    onExpandedChange = { desplegableActualAbierto = !desplegableActualAbierto }
                ) {
                    OutlinedTextField(
                        value = recetaActualSeleccionada?.let { "${it.codigo} - ${it.nombre} (${it.maquina})" } ?: "Seleccionar receta",
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = desplegableActualAbierto) },
                        modifier = Modifier
                            .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = desplegableActualAbierto,
                        onDismissRequest = { desplegableActualAbierto = false }
                    ) {
                        recetas.forEach { receta ->
                            DropdownMenuItem(
                                text = { Text("${receta.codigo} - ${receta.nombre} (${receta.maquina})") },
                                onClick = {
                                    recetaActualSeleccionada = receta
                                    desplegableActualAbierto = false
                                }
                            )
                        }
                    }
                }

                recetaActualSeleccionada?.let { receta ->
                    val volumenFijoVisual = 100.0

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                            .padding(8.dp)
                    ) {
                        receta.componentes.forEach { comp ->
                            val conc = extraerValorNumerico(comp.cant)
                            val esGramos = comp.cant.contains("g/l", ignoreCase = true)
                            val totalPresente = (volumenFijoVisual * conc) / 1000.0
                            val unidadText = if (esGramos) "g" else "L"

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("${comp.nombre} (${comp.cant})", style = MaterialTheme.typography.bodySmall)
                                Text(
                                    text = "Total: ${String.format(java.util.Locale.US, "%.1f", if (esGramos) totalPresente * 1000 else totalPresente)} $unidadText",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

                OutlinedTextField(
                    value = litrosActualTexto,
                    onValueChange = { litrosActualTexto = it.filter { c -> c.isDigit() } },
                    label = { Text("Litros Actuales (L)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
            }
        }

        // RECETA OBJETIVO
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "2. OBJETIVO",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )

                ExposedDropdownMenuBox(
                    expanded = desplegableObjetivoAbierto,
                    onExpandedChange = { desplegableObjetivoAbierto = !desplegableObjetivoAbierto }
                ) {
                    OutlinedTextField(
                        value = recetaObjetivoSeleccionada?.let { "${it.codigo} - ${it.nombre}" } ?: "Seleccionar receta objetivo",
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = desplegableObjetivoAbierto) },
                        modifier = Modifier
                            .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = desplegableObjetivoAbierto,
                        onDismissRequest = { desplegableObjetivoAbierto = false }
                    ) {
                        recetasMismaMaquina.forEach { receta ->
                            DropdownMenuItem(
                                text = { Text("${receta.codigo} - ${receta.nombre}") },
                                onClick = {
                                    recetaObjetivoSeleccionada = receta
                                    desplegableObjetivoAbierto = false
                                }
                            )
                        }
                    }
                }

                recetaObjetivoSeleccionada?.let { receta ->
                    val volumenFijoVisual = 2000.0

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                            .padding(8.dp)
                    ) {
                        receta.componentes.forEach { comp ->
                            val conc = extraerValorNumerico(comp.cant)
                            val esGramos = comp.cant.contains("g/l", ignoreCase = true)
                            val totalNecesario = (volumenFijoVisual * conc) / 1000.0
                            val unidadText = if (esGramos) "Kg" else "L"

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("${comp.nombre} (${comp.cant})", style = MaterialTheme.typography.bodySmall)
                                Text(
                                    text = "Total: ${String.format(java.util.Locale.US, "%.1f", totalNecesario)} $unidadText",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

                OutlinedTextField(
                    value = litrosObjetivoTexto,
                    onValueChange = { litrosObjetivoTexto = it.filter { c -> c.isDigit() } },
                    label = { Text("Litros Objetivo (L)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
            }
        }

        Button(
            onClick = { /*  */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Icon(Icons.Default.Calculate, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("CALCULAR AJUSTE DE BAÑO", fontWeight = FontWeight.Bold)
        }

        // RESULTADO DE PREPARACIÓN

        val resultadosSimulados = listOf(
            AdicionResultado("AGUA", "", 1720.0, "L", false, true),
            AdicionResultado("SOSA CÁUSTICA", "40 cc/l", 32.0, "L", false),
            AdicionResultado("TANADYE BOOSTER", "6 cc/l", 12.0, "L", true),
            AdicionResultado("PERSULFATO SÓDICO", "5 g/l", 10.0, "Kg", true)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "PREPARACIÓN ORDENADOR",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Tanque Ajuste\n2000 L",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                HorizontalDivider()

                resultadosSimulados.forEach { adicion ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = if (adicion.esMano) "✋ " else "🔷 ",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Column {
                                Text(
                                    text = adicion.nombre,
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Bold
                                )
                                if (adicion.concentracion.isNotBlank()) {
                                    Text(
                                        text = "Conc: ${adicion.concentracion}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }

                        Text(
                            text = "${String.format(java.util.Locale.US, "%.1f", adicion.cantidad)} ${adicion.unidad}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

private fun extraerValorNumerico(texto: String): Double {
    val limpio = texto.replace("cc/l", "", ignoreCase = true)
        .replace("g/l", "", ignoreCase = true)
        .trim()
        .replace(",", ".")
    return limpio.toDoubleOrNull() ?: 0.0
}

@Preview(showBackground = true)
@Composable
fun AjusteScreenPreview() {
    val mockRecetas = listOf(
        Receta(
            codigo = 68,
            nombre = "IMPREG. ALGODON 100%",
            maquina = "OSTHOFF",
            componentes = listOf(
                Componente("SOSA CÁUSTICA", "40 cc/l", "false"),
                Componente("FELOSAN FOX", "8 cc/l", "false"),
                Componente("CONTAVAN ICE", "5 cc/l", "false"),
                Componente("AGUA OXIGENADA 50%", "50 cc/l", "false"),
                Componente("PERSULFATO SÓDICO", "5 g/l", "true")
            )
        ),
        Receta(
            codigo = 27944,
            nombre = "IMPREG. ALGODÓN DIFÍCIL",
            maquina = "OSTHOFF",
            componentes = listOf(
                Componente("SOSA CÁUSTICA", "40 cc/l", "false"),
                Componente("TANNEX HC-BS", "4 cc/l", "true"),
                Componente("TANADYE BOOSTER", "6 cc/l", "true"),
                Componente("TANATERGE ADVANCE", "4 cc/l", "true"),
                Componente("AGUA OXIGENADA 50%", "50 cc/l", "false"),
                Componente("PERSULFATO SÓDICO", "5 g/l", "true")
            )
        )
    )

    MaterialTheme {
        AjusteScreen(
            recetas = mockRecetas,
            onVolverClick = {}
        )
    }
}
