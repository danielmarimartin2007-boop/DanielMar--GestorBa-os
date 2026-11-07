package com.example.gestorbaos.validacion

import com.example.gestorbaos.model.Componente
import com.example.gestorbaos.model.Receta

data class ErrorComponente(
    val errorNombre: String? = null,
    val errorCantidad: String? = null
)

object FormularioValidator {

    fun validarCodigo(
        codigo: String,
        recetasExistentes: List<Receta> = emptyList(),
        recetaInicialId: Int? = null
    ): String? {
        val codigoInt = codigo.toIntOrNull()
        return when {
            codigo.isBlank() -> "El código es obligatorio"
            codigoInt == null -> "Debe ser un número válido"
            codigoInt <= 0 -> "El código debe ser mayor a 0"
            recetasExistentes.any { it.codigo == codigoInt && it.codigo != recetaInicialId } -> "El código ya existe"
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

    fun validarNombreComponente(nombre: String): String? {
        return when {
            nombre.isBlank() -> "Nombre obligatorio"
            nombre.length < 2 -> "Mínimo 2 letras"
            else -> null
        }
    }

    fun validarCantidad(cant: String): String? {
        val numeroTexto = cant.replace("g/l", "", ignoreCase = true)
            .replace("cc/l", "", ignoreCase = true)
            .trim()
            .replace(",", ".")

        return when {
            cant.isBlank() || numeroTexto.isBlank() -> "Cantidad obligatoria"
            numeroTexto.toDoubleOrNull() == null -> "Número inválido"
            numeroTexto.toDouble() <= 0 -> "Debe ser > 0"
            else -> null
        }
    }

    fun validarComponentes(componentes: List<Componente>): List<ErrorComponente> {
        return componentes.map { comp ->
            ErrorComponente(
                errorNombre = validarNombreComponente(comp.nombre),
                errorCantidad = validarCantidad(comp.cant)
            )
        }
    }
}