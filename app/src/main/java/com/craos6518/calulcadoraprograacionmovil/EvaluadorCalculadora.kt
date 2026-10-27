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

        // Reemplazar operadores visuales por operadores reconocidos por JavaScript
        val expresionParaRhino = expresion
            .replace("×", "*")
            .replace("÷", "/")
            .replace(",", ".")

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
}
