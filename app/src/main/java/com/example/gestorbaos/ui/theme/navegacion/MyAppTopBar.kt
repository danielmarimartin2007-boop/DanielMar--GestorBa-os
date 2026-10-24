package com.example.gestorbaos.ui.theme.navegacion

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppTopBar(
    navController: NavController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val esDetalle = currentRoute?.startsWith("detalle/") == true

    TopAppBar(
        title = {
            Text(
                text = when (currentRoute) {
                    Rutas.HOME -> "Gestor de Baños ROYO"
                    Rutas.RECETAS -> "Catálogo de Recetas"
                    Rutas.HISTORIAL -> "Historial de Baños"
                    Rutas.PERFIL -> "Mi Perfil"
                    else -> if (esDetalle) "Detalle de Receta" else ""
                },
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            if (esDetalle) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    )
}