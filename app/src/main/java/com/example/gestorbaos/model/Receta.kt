package com.example.gestorbaos.model

object Maquina {
    const val OSTHOFF = "OSTHOFF"
    const val BRUGMAN = "BRUGMAN 1"
    const val SANFOR = "SANFOR"
}

data class Componente(
    val nombre: String,
    val cant: String,
    val mano: String
)

data class Receta(
    val codigo: Int,
    val nombre: String,
    val maquina: String,
    val componentes: List<Componente> = emptyList(),
)