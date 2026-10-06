package com.craos6518.calulcadoraprograacionmovil

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.mozilla.javascript.Context
import org.mozilla.javascript.Scriptable

class ModeloVistaCalculadora : ViewModel() {

    private val _ecuacionText = MutableLiveData<String>("")
    val ecuacionText : LiveData<String> = _ecuacionText

    private val _resultadoText = MutableLiveData<String>("0")
    val resultadosText : LiveData<String> = _resultadoText

    fun ClickBoton(btn : String){
        Log.i("ModeloVistaCalculadora", "Click Boton: $btn")

        _ecuacionText.value?.let { currentEquation ->
            if (btn == "AC"){
                _ecuacionText.value = ""
                _resultadoText.value = "0"
                return
            }
            if (btn == "C"){
                if (currentEquation.isNotEmpty()){
                    val nuevaEcuacion = currentEquation.substring(0, currentEquation.length - 1)
                    _ecuacionText.value = nuevaEcuacion
                    if (nuevaEcuacion.isNotEmpty()) {
                        try {
                            val res = CalcularResultado(nuevaEcuacion)
                            if (res.isNotEmpty() && res != "org.mozilla.javascript.Undefined@0") {
                                _resultadoText.value = res
                            }
                        } catch (_: Exception) {
                            // Ignore
                        }
                    } else {
                        _resultadoText.value = "0"
                    }
                    return
                }
                return
            }
            if (btn == "="){
                if (currentEquation.isNotEmpty()) {
                    val res = CalcularResultado(currentEquation)
                    if (res.isNotEmpty() && res != "org.mozilla.javascript.Undefined@0") {
                        _resultadoText.value = res
                        _ecuacionText.value = res
                    }
                }
                return
            }

            if (btn == "+/-") {
                if (currentEquation.startsWith("-")) {
                    _ecuacionText.value = currentEquation.substring(1)
                } else if (currentEquation.isNotEmpty()) {
                    _ecuacionText.value = "-$currentEquation"
                }
                return
            }

            val evalBtn = if (btn == "%") "/100" else btn
            val nuevaEcuacion = currentEquation + evalBtn
            _ecuacionText.value = nuevaEcuacion

            try {
                val res = CalcularResultado(nuevaEcuacion)
                if (res.isNotEmpty() && res != "org.mozilla.javascript.Undefined@0") {
                    _resultadoText.value = res
                }
            } catch (_: Exception) {
                // Ignore partial evaluation errors
            }
        }
    }

    fun CalcularResultado(ecuacion : String) : String{
        val contexto : Context = Context.enter()
        try {
            contexto.optimizationLevel = -1
            val scriptable : Scriptable = contexto.initStandardObjects()
            val ecuacionJS = ecuacion.replace("X", "*").replace(",", ".")
            val resultadoObj = contexto.evaluateString(scriptable, ecuacionJS, "Javascript", 1, null)
            var resultadoFinal = resultadoObj?.toString() ?: ""
            if (resultadoFinal.endsWith(".0")){
                resultadoFinal = resultadoFinal.removeSuffix(".0")
            }
            return resultadoFinal
        } catch (_: Exception) {
            return ""
        } finally {
            Context.exit()
        }
    }
}