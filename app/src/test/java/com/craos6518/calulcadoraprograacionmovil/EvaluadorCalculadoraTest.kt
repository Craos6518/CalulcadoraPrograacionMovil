package com.craos6518.calulcadoraprograacionmovil

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class EvaluadorCalculadoraTest {

    private lateinit var evaluador: EvaluadorCalculadora

    @Before
    fun setUp() {
        evaluador = EvaluadorCalculadora()
    }

    @Test
    fun test1_evaluarSuma_retornaExito() {
        val resultado = evaluador.evaluar("12+8")
        assertTrue(resultado is ResultadoEvaluacion.Exito)
        assertEquals("20.0", (resultado as ResultadoEvaluacion.Exito).valor)
    }

    @Test
    fun test2_evaluarResta_retornaExito() {
        val resultado = evaluador.evaluar("50-15")
        assertTrue(resultado is ResultadoEvaluacion.Exito)
        assertEquals("35.0", (resultado as ResultadoEvaluacion.Exito).valor)
    }

    @Test
    fun test3_evaluarMultiplicacion_retornaExito() {
        val resultado = evaluador.evaluar("6×7")
        assertTrue(resultado is ResultadoEvaluacion.Exito)
        assertEquals("42.0", (resultado as ResultadoEvaluacion.Exito).valor)
    }

    @Test
    fun test4_evaluarDivision_retornaExito() {
        val resultado = evaluador.evaluar("81÷9")
        assertTrue(resultado is ResultadoEvaluacion.Exito)
        assertEquals("9.0", (resultado as ResultadoEvaluacion.Exito).valor)
    }

    @Test
    fun test5_evaluarExpresionConDecimales_retornaExito() {
        val resultado = evaluador.evaluar("3,5+2,5")
        assertTrue(resultado is ResultadoEvaluacion.Exito)
        assertEquals("6.0", (resultado as ResultadoEvaluacion.Exito).valor)
    }

    @Test
    fun test6_evaluarExpresionVacia_retornaError() {
        val resultado = evaluador.evaluar("")
        assertTrue(resultado is ResultadoEvaluacion.Error)
    }

    @Test
    fun test7_evaluarDivisionPorCero_retornaError() {
        val resultado = evaluador.evaluar("10÷0")
        assertTrue(resultado is ResultadoEvaluacion.Error)
    }

    @Test
    fun test8_evaluarCaracteresInvalidos_retornaError() {
        val resultado = evaluador.evaluar("abc+123")
        assertTrue(resultado is ResultadoEvaluacion.Error)
    }

    @Test
    fun test9_evaluarSintaxisInvalida_retornaError() {
        val resultado = evaluador.evaluar("5++")
        assertTrue(resultado is ResultadoEvaluacion.Error)
    }

    @Test
    fun test10_evaluarExpresionCombinada_retornaExito() {
        val resultado = evaluador.evaluar("10+5×2")
        assertTrue(resultado is ResultadoEvaluacion.Exito)
        assertEquals("20.0", (resultado as ResultadoEvaluacion.Exito).valor)
    }
}
