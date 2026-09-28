package com.example.gestorbaos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gestorbaos.model.Maquina
import com.example.gestorbaos.model.Receta
import com.example.gestorbaos.ui.theme.GestorBañosTheme
import com.example.gestorbaos.ui.theme.screen.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GestorBañosTheme{
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(8.dp),
                        recetas =  listOf(
                            Receta(nombre = "TOB ALGODON", maquina = Maquina.SANFOR, codigo = 15),
                            Receta(nombre = "BLANQUEO+MERCERIZADO", maquina = Maquina.BRUGMAN, codigo = 67),
                            Receta(nombre = "IMPREG. ALGODÓN DIFÍCIL", maquina = Maquina.OSTHOFF, codigo = 27944),
                            Receta(nombre = "SANFOR TOB NUEVO", maquina = Maquina.SANFOR, codigo = 13552),
                            Receta(nombre = "T.P.CO 100% SILICONA", maquina = Maquina.SANFOR, codigo = 17908),
                            Receta(nombre = "OSTHOFF IMPREG. MEZCLAS", maquina = Maquina.OSTHOFF, codigo = 70)
                        )
                    )
                }
            }
        }
    }
}