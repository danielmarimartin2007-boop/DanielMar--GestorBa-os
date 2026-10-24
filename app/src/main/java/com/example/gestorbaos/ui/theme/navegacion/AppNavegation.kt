package com.example.gestorbaos.ui.theme.navegacion

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.gestorbaos.model.Componente
import com.example.gestorbaos.model.Maquina
import com.example.gestorbaos.model.Receta
import com.example.gestorbaos.ui.theme.screen.DetalleScreen
import com.example.gestorbaos.ui.theme.screen.HistorialScreen
import com.example.gestorbaos.ui.theme.screen.HomeScreen
import com.example.gestorbaos.ui.theme.screen.PerfilScreen
import com.example.gestorbaos.ui.theme.screen.RecetasScreen

object Rutas {
    const val HOME = "home"
    const val RECETAS = "recetas"
    const val HISTORIAL = "historial"
    const val PERFIL = "perfil"
    const val DETALLE = "detalle/{codigo}"

    fun detalleConId(codigo: Int) = "detalle/$codigo"
}

val baseDeDatosRecetas = listOf(
    Receta(
        codigo = 15,
        nombre = "TOB ALGODON",
        maquina = Maquina.SANFOR,
        componentes = listOf(
            Componente(nombre = "TUBINGAL RNJ", cant = "18 cc/l", mano = "false"),
            Componente(nombre = "ÁCIDO ACÉTICO 80%", cant = "3 cc/l", mano = "false"),
            Componente(nombre = "AUXICOL", cant = "0,7 cc/l", mano = "true")
        )
    ),
    Receta(
        codigo = 67,
        nombre = "BLANQUEO+MERCERIZADO",
        maquina = Maquina.BRUGMAN,
        componentes = listOf(
            Componente(nombre = "SOSA CÁUSTICA", cant = "30 cc/l", mano = "false"),
            Componente(nombre = "TANACID UNA", cant = "30 cc/l", mano = "false"),
            Componente(nombre = "FELOSAN FOX", cant = "19 cc/l", mano = "false"),
            Componente(nombre = "AGUA OXIGENADA 50%", cant = "45 cc/l", mano = "false")
        )
    ),
    Receta(
        codigo = 27944,
        nombre = "IMPREG. ALGODÓN DIFÍCIL",
        maquina = Maquina.OSTHOFF,
        componentes = listOf(
            Componente(nombre = "SOSA CÁUSTICA", cant = "40 cc/l", mano = "true"),
            Componente(nombre = "TANNEX HC-BS", cant = "4 cc/l", mano = "true"),
            Componente(nombre = "TANADYE BOOSTER", cant = "6 cc/l", mano = "true"),
            Componente(nombre = "AGUA OXIGENADA 50%", cant = "50 cc/l", mano = "false"),
            Componente(nombre = "PERSULFATO SÓDICO", cant = "5 g/l", mano = "true")
        )
    )
)

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Rutas.HOME,
        modifier = modifier
    ) {
        composable(Rutas.HOME) {
            HomeScreen(
                recetasFavoritas = baseDeDatosRecetas.take(2),
                onRecetaClick = { navController.navigate(Rutas.detalleConId(it)) }
            )
        }

        composable(Rutas.RECETAS) {
            RecetasScreen(
                recetas = baseDeDatosRecetas,
                onRecetaClick = { navController.navigate(Rutas.detalleConId(it)) }
            )
        }

        composable(Rutas.HISTORIAL) {
            val historialReal = listOf(
                com.example.gestorbaos.ui.theme.screen.RegistroHistorial(
                    codigoAjuste = 104,
                    recetaOrigen = "TOB ALGODON",
                    recetaDestino = "SANFOR TOB NUEVO",
                    maquina = "SANFOR",
                    fecha = "24/10/2026 - 10:30 AM"
                ),
                com.example.gestorbaos.ui.theme.screen.RegistroHistorial(
                    codigoAjuste = 103,
                    recetaOrigen = "IMPREG. ALGODÓN DIFÍCIL",
                    recetaDestino = "OSTHOFF IMPREG. MEZCLAS",
                    maquina = "OSTHOFF",
                    fecha = "23/10/2026 - 16:45 PM"
                ),
                com.example.gestorbaos.ui.theme.screen.RegistroHistorial(
                    codigoAjuste = 102,
                    recetaOrigen = "BLANQUEO+MERCERIZADO",
                    recetaDestino = "LAVADO TINTURA B1",
                    maquina = "BRUGMAN 1",
                    fecha = "22/10/2026 - 09:15 AM"
                )
            )
            HistorialScreen(historial = historialReal)
        }

        composable(Rutas.PERFIL) {
            PerfilScreen()
        }

        composable(
            route = Rutas.DETALLE,
            arguments = listOf(navArgument("codigo") { type = NavType.IntType })
        ) { backStackEntry ->
            val codigo = backStackEntry.arguments?.getInt("codigo") ?: 0
            val recetaSeleccionada = buscarReceta(codigo)

            DetalleScreen(receta = recetaSeleccionada)
        }
    }
}

fun buscarReceta(codigo: Int): Receta {
    return baseDeDatosRecetas.find { it.codigo == codigo } ?: baseDeDatosRecetas.first()
}
