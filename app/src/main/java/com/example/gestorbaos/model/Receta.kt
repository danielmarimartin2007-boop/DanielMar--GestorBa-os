package com.example.gestorbaos.model

object Maquina{
    const val  OSTHOFF = "OSTHOFF"
    const val BRUGMAN = "BRUGMAN 1"
    const val  SANFOR = "SANFOR"
}

data class Receta(
    val nombre: String,
    val maquina: String,
    val codigo: Int
)