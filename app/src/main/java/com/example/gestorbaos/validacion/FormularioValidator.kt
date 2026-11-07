package com.example.gestorbaos.validacion

import com.example.gestorbaos.model.Componente

object FormularioValidator {

    fun validarCodigo(codigo: String): String? {
        return when {
            codigo.isBlank() -> "El código es obligatorio"
            codigo.toIntOrNull() == null -> "Debe ser un número válido"
            codigo.toInt() <= 0 -> "El código debe ser mayor a 0"
            else -> null
        }
    }

    fun validarNombre(nombre: String): String? {
        return when {
            nombre.isBlank() -> "El nombre es obligatorio"
            nombre.length < 3 -> "El nombre debe tener al menos 3 letras"
            else -> null
        }
    }

    fun validarCantidad(cant: String): String? {
        val numeroTexto = cant.replace("g/l", "", ignoreCase = true)
            .replace("cc/l", "", ignoreCase = true)
            .trim()
            .replace(",", ".")

        return when {
            cant.isBlank() || numeroTexto.isBlank() -> "La cantidad no puede estar vacía"
            numeroTexto.toDoubleOrNull() == null -> "La cantidad debe ser un número válido"
            numeroTexto.toDouble() <= 0 -> "La cantidad debe ser mayor a 0"
            else -> null
        }
    }

    fun validarComponentes(componentes: List<Componente>): Boolean {
        if (componentes.isEmpty()) return true

        return componentes.all { componente ->
            componente.nombre.isNotBlank() && validarCantidad(componente.cant) == null
        }
    }
}