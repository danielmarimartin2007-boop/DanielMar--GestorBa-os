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
import androidx.compose.material3.ExposedDropdownMenuAnchorType
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
    var actualExpanded by remember { mutableStateOf(false) }

    var selectedActual by remember(recetaInicialId, recetas) {
        mutableStateOf(
            recetas.find { it.codigo == recetaInicialId } ?: recetas.firstOrNull()
        )
    }
    var litrosActualText by remember { mutableStateOf("100") }

    var objetivoExpanded by remember { mutableStateOf(false) }
    val recetasMismaMaquina = remember(selectedActual, recetas) {
        recetas.filter { it.maquina == selectedActual?.maquina }
    }
    var selectedObjetivo by remember(selectedActual, recetasMismaMaquina) {
        mutableStateOf(
            recetasMismaMaquina.getOrNull(1) ?: recetasMismaMaquina.firstOrNull()
        )
    }
    var litrosObjetivoText by remember { mutableStateOf("2000") }

    var listaResultados by remember { mutableStateOf<List<AdicionResultado>?>(null) }

    LaunchedEffect(selectedActual) {
        if (selectedObjetivo?.maquina != selectedActual?.maquina) {
            selectedObjetivo = recetasMismaMaquina.firstOrNull { it.codigo != selectedActual?.codigo }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onVolverClick) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
            }
            Text(
                text = "Ajuste de Baño",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        }

        // ---------------- 1. RECETA ACTUAL ----------------
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
                    expanded = actualExpanded,
                    onExpandedChange = { actualExpanded = !actualExpanded }
                ) {
                    OutlinedTextField(
                        value = selectedActual?.let { "${it.codigo} - ${it.nombre} (${it.maquina})" } ?: "Seleccionar receta",
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = actualExpanded) },
                        modifier = Modifier
                            .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = actualExpanded,
                        onDismissRequest = { actualExpanded = false }
                    ) {
                        recetas.forEach { receta ->
                            DropdownMenuItem(
                                text = { Text("${receta.codigo} - ${receta.nombre} (${receta.maquina})") },
                                onClick = {
                                    selectedActual = receta
                                    actualExpanded = false
                                }
                            )
                        }
                    }
                }

                selectedActual?.let { receta ->
                    val volActual = litrosActualText.toDoubleOrNull() ?: 0.0
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
                            val totalPresente = (volActual * conc) / 1000.0
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
                    value = litrosActualText,
                    onValueChange = { litrosActualText = it.filter { c -> c.isDigit() } },
                    label = { Text("Litros Actuales (L)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
            }
        }

        // ---------------- 2. RECETA OBJETIVO ----------------
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
                    expanded = objetivoExpanded,
                    onExpandedChange = { objetivoExpanded = !objetivoExpanded }
                ) {
                    OutlinedTextField(
                        value = selectedObjetivo?.let { "${it.codigo} - ${it.nombre}" } ?: "Seleccionar receta objetivo",
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = objetivoExpanded) },
                        modifier = Modifier
                            .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = objetivoExpanded,
                        onDismissRequest = { objetivoExpanded = false }
                    ) {
                        recetasMismaMaquina.forEach { receta ->
                            DropdownMenuItem(
                                text = { Text("${receta.codigo} - ${receta.nombre}") },
                                onClick = {
                                    selectedObjetivo = receta
                                    objetivoExpanded = false
                                }
                            )
                        }
                    }
                }

                selectedObjetivo?.let { receta ->
                    val volObj = litrosObjetivoText.toDoubleOrNull() ?: 0.0
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
                            val totalNecesario = (volObj * conc) / 1000.0
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
                    value = litrosObjetivoText,
                    onValueChange = { litrosObjetivoText = it.filter { c -> c.isDigit() } },
                    label = { Text("Litros Objetivo (L)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
            }
        }

        Button(
            onClick = {
                val volA = litrosActualText.toDoubleOrNull() ?: 0.0
                val volO = litrosObjetivoText.toDoubleOrNull() ?: 0.0

                if (selectedActual != null && selectedObjetivo != null && volO > 0) {
                    listaResultados = calcularAjuste(
                        recetaActual = selectedActual!!,
                        volumenActual = volA,
                        recetaObjetivo = selectedObjetivo!!,
                        volumenObjetivo = volO
                    )
                }
            },
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

        // ---------------- 3. RESULTADO DE PREPARACIÓN ----------------
        listaResultados?.let { resultados ->
            val volO = litrosObjetivoText.toDoubleOrNull() ?: 0.0

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
                        text = "Tanque Ajuste\n${volO.toInt()} L",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    HorizontalDivider()

                    resultados.forEach { adicion ->
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
}

// ---------------- FUNCIONES DE CÁLCULO DE AJUSTE ----------------

private fun extraerValorNumerico(texto: String): Double {
    val limpio = texto.replace("cc/l", "", ignoreCase = true)
        .replace("g/l", "", ignoreCase = true)
        .trim()
        .replace(",", ".")
    return limpio.toDoubleOrNull() ?: 0.0
}

private fun calcularAjuste(
    recetaActual: Receta,
    volumenActual: Double,
    recetaObjetivo: Receta,
    volumenObjetivo: Double
): List<AdicionResultado> {
    val resultados = mutableListOf<AdicionResultado>()
    var acumuladoLitrosQuimicos = 0.0

    recetaObjetivo.componentes.forEach { compObj ->
        val concObj = extraerValorNumerico(compObj.cant)
        val esGramos = compObj.cant.contains("g/l", ignoreCase = true)
        val esMano = compObj.mano.equals("true", ignoreCase = true)

        val compAct = recetaActual.componentes.find { it.nombre.equals(compObj.nombre, ignoreCase = true) }
        val concAct = compAct?.let { extraerValorNumerico(it.cant) } ?: 0.0

        val necesidadTotal = (volumenObjetivo * concObj) / 1000.0
        val presenteActual = (volumenActual * concAct) / 1000.0
        val cantidadAnadir = (necesidadTotal - presenteActual).coerceAtLeast(0.0)

        val unidad = if (esGramos) "Kg" else "L"

        if (!esGramos) {
            acumuladoLitrosQuimicos += cantidadAnadir
        }

        resultados.add(
            AdicionResultado(
                nombre = compObj.nombre,
                concentracion = compObj.cant,
                cantidad = cantidadAnadir,
                unidad = unidad,
                esMano = esMano
            )
        )
    }

    val aguaNecesaria = ((volumenObjetivo - volumenActual) - acumuladoLitrosQuimicos).coerceAtLeast(0.0)

    resultados.add(
        0,
        AdicionResultado(
            nombre = "AGUA",
            concentracion = "",
            cantidad = aguaNecesaria,
            unidad = "L",
            esMano = false,
            esAgua = true
        )
    )

    return resultados
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
