package com.example.gestorbaos.ui.theme.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gestorbaos.model.Receta
import com.example.gestorbaos.ui.theme.components.RecetaCard
import com.example.gestorbaos.model.Maquina


@Composable
fun MainScreen (
    modifier: Modifier,
    recetas: List<Receta>
){
    Column(
        modifier = modifier
    ) {
        Text(text= "Gestor de Baños ROYO", style = MaterialTheme.typography.displaySmall)
        Spacer(modifier = Modifier.height(8.dp))
        RecetaList(
            modifier = Modifier.fillMaxWidth(),
            recetas = recetas
        )
    }
}

@Composable
fun RecetaList(
    modifier: Modifier,
    recetas: List<Receta>
){
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(20){
            RecetaCard(
                modifier = Modifier.fillMaxWidth(),
                receta = recetas.random())
        }
    }
}
@Preview(showBackground = true)
@Composable
fun RecetaListPreview(){
    RecetaList(
        modifier = Modifier,
        recetas = listOf(
            Receta(nombre = "TOB ALGODON", maquina = Maquina.SANFOR, codigo = 15),
            Receta(nombre = "BLANQUEO+MERCERIZADO", maquina = Maquina.BRUGMAN, codigo = 67),
            Receta(nombre = "IMPREG. ALGODÓN DIFÍCIL", maquina = Maquina.OSTHOFF, codigo = 27944)
        )
    )
}