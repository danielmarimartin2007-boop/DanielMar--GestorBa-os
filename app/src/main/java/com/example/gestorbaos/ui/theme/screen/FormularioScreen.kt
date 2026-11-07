package com.example.gestorbaos.ui.theme.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gestorbaos.model.Componente
import com.example.gestorbaos.model.Receta
import com.example.gestorbaos.validacion.FormularioValidator

private fun extraerNumero(cant: String): String {
    return cant.replace("g/l", "", ignoreCase = true)
        .replace("cc/l", "", ignoreCase = true)
        .trim()
}

private fun extraerUnidad(cant: String): String {
    return if (cant.contains("g/l", ignoreCase = true)) "g/l" else "cc/l"
}

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun FormularioScreen(
    modifier: Modifier = Modifier,
    recetaInicial: Receta? = null,
    onSaveClick: (Receta) -> Unit,
    onCancelarClick: () -> Unit
) {
    val maquinaOptions = listOf("SANFOR", "BRUGMAN", "OSTHOFF")

    var codigo by remember { mutableStateOf(recetaInicial?.codigo?.toString() ?: "") }
    var nombre by remember { mutableStateOf(recetaInicial?.nombre ?: "") }
    var maquinaIndex by remember {
        mutableIntStateOf(maquinaOptions.indexOf(recetaInicial?.maquina ?: "SANFOR").coerceAtLeast(0))
    }

    val componentesList = remember {
        mutableStateListOf<Componente>().apply {
            recetaInicial?.componentes?.let { addAll(it) }

            while (this.size < 2) {
                add(Componente(nombre = "", cant = "", mano = "false"))
            }
        }
    }

    var numComponentes by remember { mutableIntStateOf(componentesList.size.coerceIn(2, 8)) }

    var errorCodigo by remember { mutableStateOf<String?>(null) }
    var errorNombre by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                modifier = Modifier.weight(0.4f),
                value = codigo,
                onValueChange = { codigo = it.filter { char -> char.isDigit() } },
                label = { Text("Código") },
                isError = errorCodigo != null,
                supportingText = { errorCodigo?.let { Text(text = it, color = MaterialTheme.colorScheme.error) } },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                modifier = Modifier.weight(0.6f),
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre de la Receta") },
                isError = errorNombre != null,
                supportingText = { errorNombre?.let { Text(text = it, color = MaterialTheme.colorScheme.error) } }
            )
        }

        Text("Máquina", style = MaterialTheme.typography.titleMedium)
        SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
            maquinaOptions.forEachIndexed { index, label ->
                SegmentedButton(
                    shape = SegmentedButtonDefaults.itemShape(index = index, count = maquinaOptions.size),
                    onClick = { maquinaIndex = index },
                    selected = index == maquinaIndex,
                    label = { Text(label) }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text("Número de Componentes", style = MaterialTheme.typography.titleMedium)
        SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
            val rangoComponentes = (2..8).toList()
            rangoComponentes.forEachIndexed { posicionIndex, cantidadObjetivo ->
                SegmentedButton(
                    shape = SegmentedButtonDefaults.itemShape(index = posicionIndex, count = rangoComponentes.size),
                    onClick = {
                        numComponentes = cantidadObjetivo
                        if (cantidadObjetivo > componentesList.size) {
                            repeat(cantidadObjetivo - componentesList.size) {
                                componentesList.add(Componente(nombre = "", cant = "", mano = "false"))
                            }
                        } else if (cantidadObjetivo < componentesList.size) {
                            repeat(componentesList.size - cantidadObjetivo) {
                                componentesList.removeLast()
                            }
                        }
                    },
                    selected = cantidadObjetivo == numComponentes,
                    label = { Text(cantidadObjetivo.toString()) }
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Componente", modifier = Modifier.weight(1.3f), fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodySmall)
                Text("Cantidad", modifier = Modifier.weight(1.5f), fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodySmall)
                Text("A Mano", modifier = Modifier.weight(0.7f), fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodySmall)
            }

            componentesList.forEachIndexed { index, comp ->
                val isMano = comp.mano.equals("true", ignoreCase = true)
                val numeroActual = extraerNumero(comp.cant)
                val unidadActual = extraerUnidad(comp.cant)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = comp.nombre,
                        onValueChange = { componentesList[index] = comp.copy(nombre = it) },
                        modifier = Modifier.weight(1.3f),
                        singleLine = true
                    )

                    Row(
                        modifier = Modifier.weight(1.5f),
                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = numeroActual,
                            onValueChange = { input ->
                                val filtered = input.filter { it.isDigit() || it == '.' || it == ',' }
                                val nuevaCant = if (filtered.isBlank()) "" else "$filtered $unidadActual"
                                componentesList[index] = comp.copy(cant = nuevaCant)
                            },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            placeholder = { Text("0") }
                        )

                        OutlinedButton(
                            onClick = {
                                val nuevaUnidad = if (unidadActual == "cc/l") "g/l" else "cc/l"
                                val num = extraerNumero(comp.cant)
                                val nuevaCant = if (num.isBlank()) "" else "$num $nuevaUnidad"
                                componentesList[index] = comp.copy(cant = nuevaCant)
                            },
                            modifier = Modifier.height(56.dp),
                            contentPadding = PaddingValues(horizontal = 4.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                contentColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text(unidadActual, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                        }
                    }

                    FilledIconToggleButton(
                        checked = isMano,
                        onCheckedChange = { checked ->
                            componentesList[index] = comp.copy(mano = checked.toString())
                        },
                        modifier = Modifier
                            .weight(0.7f)
                            .height(56.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = IconButtonDefaults.filledIconToggleButtonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                            checkedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            checkedContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    ) {
                        Text("✋", style = MaterialTheme.typography.titleLarge)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End)
        ) {
            OutlinedButton(
                onClick = { onCancelarClick() },
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.error),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Cancelar", fontWeight = FontWeight.Bold)
            }

            Button(
                onClick = {
                    errorCodigo = FormularioValidator.validarCodigo(codigo)
                    errorNombre = FormularioValidator.validarNombre(nombre)

                    val componentesValidos = FormularioValidator.validarComponentes(componentesList)

                    if (errorCodigo == null && errorNombre == null && componentesValidos) {
                        onSaveClick(
                            Receta(
                                codigo = codigo.toIntOrNull() ?: 0,
                                nombre = nombre,
                                maquina = maquinaOptions[maquinaIndex],
                                componentes = componentesList.toList()
                            )
                        )
                    }
                }
            ) {
                Text("Guardar Receta", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Preview(showBackground = true)
@Composable
fun RecetaFormScreenPreview() {
    MaterialTheme {
        FormularioScreen(
            modifier = Modifier.fillMaxSize(),
            onSaveClick = { },
            onCancelarClick = { },
            recetaInicial = null
        )
    }
}