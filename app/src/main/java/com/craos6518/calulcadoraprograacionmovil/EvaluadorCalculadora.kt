package com.craos6518.calulcadoraprograacionmovil

import org.mozilla.javascript.Context

sealed class ResultadoEvaluacion {
    data class Exito(val valor: String) : ResultadoEvaluacion()
    data object Error : ResultadoEvaluacion()
}

class EvaluadorCalculadora {
    fun evaluar(expresion: String): ResultadoEvaluacion {
        if (expresion.isBlank()) {
            return ResultadoEvaluacion.Error
        }

        // Validar caracteres permitidos
        val caracteresPermitidos = Regex("""[0-9+\-×÷*/%().,\s]+""")
        if (!caracteresPermitidos.matches(expresion)) {
            return ResultadoEvaluacion.Error
        }

        // 1. Reemplazar operadores visuales por operadores reconocidos por JavaScript
        var expresionParaRhino = expresion
            .replace("×", "*")
            .replace("÷", "/")
            .replace(",", ".")

        // 2. Convertir los porcentajes (%) a su equivalente matemático
        expresionParaRhino = procesarPorcentajes(expresionParaRhino)

        val contexto = Context.enter()
        return try {
            contexto.optimizationLevel = -1
            val scope = contexto.initStandardObjects()
            val objeto = contexto.evaluateString(scope, expresionParaRhino, "Calculadora", 1, null)
            val numero = objeto.toString().toDoubleOrNull()

            if ((numero != null) && numero.isFinite()) {
                ResultadoEvaluacion.Exito(numero.toString())
            } else {
                ResultadoEvaluacion.Error
            }
        } catch (_: Exception) {
            ResultadoEvaluacion.Error
        } finally {
            Context.exit() // Liberar recursos de Rhino
        }
    }

    private fun procesarPorcentajes(expresion: String): String {
        // A) Manejar sumas y restas con porcentaje: ej. "100 + 10%" -> "100 + (100 * 10 / 100)"
        val expresionSumaResta = expresion.replace(
            Regex("""(\d+(?:\.\d+)?)\s*([\+\-])\s*(\d+(?:\.\d+)?)%""")
        ) { match ->
            val base = match.groupValues[1]
            val operador = match.groupValues[2]
            val porcentaje = match.groupValues[3]
            "$base $operador ($base * $porcentaje / 100)"
        }

        // B) Manejar cualquier otro número seguido de '%': ej. "50%" -> "(50 / 100)"
        return expresionSumaResta.replace(
            Regex("""(\d+(?:\.\d+)?)%""")
        ) { match ->
            val numero = match.groupValues[1]
            "($numero / 100)"
        }
    }
}