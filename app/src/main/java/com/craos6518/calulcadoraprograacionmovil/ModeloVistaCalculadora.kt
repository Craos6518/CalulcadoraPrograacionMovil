package com.craos6518.calulcadoraprograacionmovil

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ModeloVistaCalculadora : ViewModel() {

    private val _ecuacionText = MutableLiveData("")
    val ecuacionText: LiveData<String> = _ecuacionText

    private val _resultadoText = MutableLiveData("0")
    val resultadosText: LiveData<String> = _resultadoText

    private val _isError = MutableLiveData(false)
    val isError: LiveData<Boolean> = _isError

    private val _historial = MutableLiveData<List<String>>(emptyList())
    val historial: LiveData<List<String>> = _historial

    private val evaluador = EvaluadorCalculadora()
    private var resultadoConfirmado = false

    fun limpiarHistorial() {
        _historial.value = emptyList()
    }

    fun clickBoton(boton: String) {
        if (_isError.value == true) {
            _ecuacionText.value = ""
            _resultadoText.value = "0"
            _isError.value = false
            resultadoConfirmado = false
        }
        var expresion = _ecuacionText.value.orEmpty()

        when (boton) {
            "AC" -> {
                limpiarCalculadora()
                return
            }
            "C" -> {
                if (expresion.isNotEmpty()) {
                    expresion = expresion.dropLast(1)
                    _ecuacionText.value = expresion
                    resultadoConfirmado = false
                    actualizarResultadoParcial(expresion)
                }
                return
            }
            "=" -> {
                if (resultadoConfirmado || expresion.isBlank()) return

                when (val resultado = evaluador.evaluar(expresion)) {
                    is ResultadoEvaluacion.Exito -> {
                        val registro = "$expresion = ${resultado.valor}"
                        val historialActual = _historial.value.orEmpty()

                        if (historialActual.firstOrNull() != registro) {
                            _historial.value = listOf(registro) + historialActual
                        }

                        _resultadoText.value = resultado.valor
                        _ecuacionText.value = resultado.valor
                        _isError.value = false
                        resultadoConfirmado = true
                    }

                    ResultadoEvaluacion.Error -> {
                        _isError.value = true
                    }
                }
                return
            }
            "+/-" -> {
                expresion = cambiarSignoDelUltimo(expresion)
                _ecuacionText.value = expresion
                resultadoConfirmado = false
                actualizarResultadoParcial(expresion)
                return
            }
            "," -> {
                expresion = agregarDecimal(expresion)
            }
            "%" -> {
                if (expresion.isEmpty() || expresion.last() in "+-×÷" || expresion.endsWith("("))return
                expresion += "%"
            }
            "+", "-", "×", "÷" -> {
                if (expresion.isEmpty()) {
                    if (boton != "-") return
                } else if (expresion.last() in "+-×÷") {
                    expresion = expresion.dropLast(1)
                } else if (expresion.last() == ',') {
                    return
                }
                expresion += boton
            }
            in "0".."9" -> {
                if (resultadoConfirmado) {
                    expresion = ""
                    _resultadoText.value = "0"
                    resultadoConfirmado = false
                }
                expresion += boton
            }
            else -> return
        }
        _ecuacionText.value = expresion
        actualizarResultadoParcial(expresion)
    }

    private fun limpiarCalculadora() {
        _ecuacionText.value = ""
        _resultadoText.value = "0"
        _isError.value = false
        resultadoConfirmado = false
    }

    private fun agregarDecimal(expresion: String): String {
        val indiceOperador = expresion.indexOfLast { it in "+-×÷" }
        val ultimoNumero = expresion.substring(indiceOperador + 1)

        if (ultimoNumero.contains(",") || ultimoNumero.contains(".")) {
            return expresion
        }

        return if (expresion.isEmpty() || expresion.last() in "+-×÷") {
            "${expresion}0,"
        } else {
            "$expresion,"
        }
    }

    private fun cambiarSignoDelUltimo(expresion: String): String {
        if (expresion.isEmpty() || expresion.last() in "+-×÷") {
            return "$expresion-"
        }
        val ultimoNumero = Regex("""-?\d+(?:[,.]\d*)?$""").find(expresion)
            ?: return expresion

        val numero = ultimoNumero.value
        val reemplazo = if (numero.startsWith("-")) {
            numero.drop(1)
        } else {
            "(-$numero)"
        }
        return expresion.replaceRange(ultimoNumero.range, reemplazo)
    }

    private fun actualizarResultadoParcial(expresion: String) {
        if (expresion.isBlank() || expresion.last() in "+-×÷" || expresion == "-") {
            _isError.value = false
            return
        }
        when (val resultado = evaluador.evaluar(expresion)) {
            is ResultadoEvaluacion.Exito -> {
                _resultadoText.value = resultado.valor
                _isError.value = false
            }
            ResultadoEvaluacion.Error -> {
                _isError.value = true
            }
        }
    }
}
