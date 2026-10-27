package com.craos6518.calulcadoraprograacionmovil

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ModeloVistaCalculadoraTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ModeloVistaCalculadora

    @Before
    fun setUp() {
        viewModel = ModeloVistaCalculadora()
    }

    @Test
    fun test1_estadoInicial_ecuacionVaciaYResultadoCero() {
        assertEquals("", viewModel.ecuacionText.value)
        assertEquals("0", viewModel.resultadosText.value)
        assertEquals(false, viewModel.isError.value)
        assertTrue(viewModel.historial.value.orEmpty().isEmpty())
    }

    @Test
    fun test2_clickNumero_actualizaEcuacion() {
        viewModel.clickBoton("1")
        viewModel.clickBoton("2")
        viewModel.clickBoton("3")
        assertEquals("123", viewModel.ecuacionText.value)
    }

    @Test
    fun test3_clickAC_limpiaEcuacionYResultado() {
        viewModel.clickBoton("5")
        viewModel.clickBoton("+")
        viewModel.clickBoton("3")
        viewModel.clickBoton("AC")
        assertEquals("", viewModel.ecuacionText.value)
        assertEquals("0", viewModel.resultadosText.value)
        assertEquals(false, viewModel.isError.value)
    }

    @Test
    fun test4_clickC_eliminaUltimoCaracter() {
        viewModel.clickBoton("1")
        viewModel.clickBoton("2")
        viewModel.clickBoton("3")
        viewModel.clickBoton("C")
        assertEquals("12", viewModel.ecuacionText.value)
    }

    @Test
    fun test5_clickIgual_calculaResultadoYGuardaEnHistorial() {
        viewModel.clickBoton("9")
        viewModel.clickBoton("÷")
        viewModel.clickBoton("3")
        viewModel.clickBoton("=")
        assertEquals("3.0", viewModel.resultadosText.value)
        assertEquals(1, viewModel.historial.value?.size)
        assertEquals("9÷3 = 3.0", viewModel.historial.value?.first())
    }

    @Test
    fun test6_limpiarHistorial_vaciaLista() {
        viewModel.clickBoton("4")
        viewModel.clickBoton("+")
        viewModel.clickBoton("4")
        viewModel.clickBoton("=")
        assertFalse(viewModel.historial.value.orEmpty().isEmpty())

        viewModel.limpiarHistorial()
        assertTrue(viewModel.historial.value.orEmpty().isEmpty())
    }

    @Test
    fun test7_clickCambiarSigno_modificaExpresion() {
        viewModel.clickBoton("5")
        viewModel.clickBoton("+/-")
        assertEquals("(-5)", viewModel.ecuacionText.value)
    }

    @Test
    fun test8_clickDecimal_agregaComaCorrectamente() {
        viewModel.clickBoton("2")
        viewModel.clickBoton(",")
        viewModel.clickBoton("5")
        assertEquals("2,5", viewModel.ecuacionText.value)

        viewModel.clickBoton(",")
        assertEquals("2,5", viewModel.ecuacionText.value)
    }

    @Test
    fun test9_clickOperadorSeguido_reemplazaAnterior() {
        viewModel.clickBoton("8")
        viewModel.clickBoton("+")
        viewModel.clickBoton("-")
        assertEquals("8-", viewModel.ecuacionText.value)
    }

    @Test
    fun test10_ingresarNumeroPostResultado_reiniciaExpresion() {
        viewModel.clickBoton("2")
        viewModel.clickBoton("+")
        viewModel.clickBoton("3")
        viewModel.clickBoton("=")
        assertEquals("5.0", viewModel.resultadosText.value)

        viewModel.clickBoton("7")
        assertEquals("7", viewModel.ecuacionText.value)
    }
}
