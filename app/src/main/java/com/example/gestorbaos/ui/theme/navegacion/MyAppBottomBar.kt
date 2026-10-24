package com.example.gestorbaos.ui.theme.navegacion

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun MyAppBottomBar(
    navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    if (currentRoute in listOf(Rutas.HOME, Rutas.RECETAS, Rutas.HISTORIAL, Rutas.PERFIL)) {
        NavigationBar {
            NavigationBarItem(
                selected = currentRoute == Rutas.HOME,
                onClick = { navController.navigate(Rutas.HOME) },
                icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                label = { Text("Home") }
            )

            NavigationBarItem(
                selected = currentRoute == Rutas.RECETAS,
                onClick = { navController.navigate(Rutas.RECETAS) },
                icon = { Icon(Icons.Default.List, contentDescription = "Recetas") },
                label = { Text("Recetas") }
            )

            NavigationBarItem(
                selected = currentRoute == Rutas.HISTORIAL,
                onClick = { navController.navigate(Rutas.HISTORIAL) },
                icon = { Icon(Icons.Default.DateRange, contentDescription = "Historial") },
                label = { Text("Historial") }
            )

            NavigationBarItem(
                selected = currentRoute == Rutas.PERFIL,
                onClick = { navController.navigate(Rutas.PERFIL) },
                icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
                label = { Text("Perfil") }
            )
        }
    }
}