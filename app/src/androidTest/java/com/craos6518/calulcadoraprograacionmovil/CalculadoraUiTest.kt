package com.craos6518.calulcadoraprograacionmovil

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CalculadoraUiTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun test1_pantallaInicial_muestraBotonesPrincipales() {
        composeTestRule.onNodeWithText("AC").assertIsDisplayed()
        composeTestRule.onNodeWithText("C").assertIsDisplayed()
        composeTestRule.onNodeWithText("=").assertIsDisplayed()
        composeTestRule.onNodeWithText("0").assertIsDisplayed()
        composeTestRule.onNodeWithText("+").assertIsDisplayed()
    }

    @Test
    fun test2_presionarNumeros_muestraEcuacionYResultado() {
        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("2").performClick()
        composeTestRule.onNodeWithText("3").performClick()

        composeTestRule.onNodeWithText("123").assertIsDisplayed()
    }

    @Test
    fun test3_presionarBotonAC_limpiaPantalla() {
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("+").performClick()
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("AC").performClick()

        composeTestRule.onNodeWithText("0").assertIsDisplayed()
    }

    @Test
    fun test4_presionarBotonC_borraUltimoCaracter() {
        composeTestRule.onNodeWithText("9").performClick()
        composeTestRule.onNodeWithText("8").performClick()
        composeTestRule.onNodeWithText("7").performClick()
        composeTestRule.onNodeWithText("C").performClick()

        composeTestRule.onNodeWithText("98").assertIsDisplayed()
    }

    @Test
    fun test5_operacionSuma_calculaResultadoCorrecto() {
        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("+").performClick()
        composeTestRule.onNodeWithText("2").performClick()
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("=").performClick()

        composeTestRule.onNodeWithText("40.0").assertIsDisplayed()
    }

    @Test
    fun test6_operacionResta_calculaResultadoCorrecto() {
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("-").performClick()
        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("2").performClick()
        composeTestRule.onNodeWithText("=").performClick()

        composeTestRule.onNodeWithText("38.0").assertIsDisplayed()
    }

    @Test
    fun test7_operacionMultiplicacion_calculaResultadoCorrecto() {
        composeTestRule.onNodeWithText("7").performClick()
        composeTestRule.onNodeWithText("×").performClick()
        composeTestRule.onNodeWithText("8").performClick()
        composeTestRule.onNodeWithText("=").performClick()

        composeTestRule.onNodeWithText("56.0").assertIsDisplayed()
    }

    @Test
    fun test8_operacionDivision_calculaResultadoCorrecto() {
        composeTestRule.onNodeWithText("8").performClick()
        composeTestRule.onNodeWithText("4").performClick()
        composeTestRule.onNodeWithText("÷").performClick()
        composeTestRule.onNodeWithText("2").performClick()
        composeTestRule.onNodeWithText("=").performClick()

        composeTestRule.onNodeWithText("42.0").assertIsDisplayed()
    }

    @Test
    fun test9_navegarAHistorial_muestraPantallaHistorial() {
        composeTestRule.onNodeWithText("Historial").performClick()

        composeTestRule.onNodeWithText("Todavía no hay operaciones").assertIsDisplayed()
    }

    @Test
    fun test10_operacionRealizada_seGuardaEnHistorial() {
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("+").performClick()
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("=").performClick()

        composeTestRule.onNodeWithText("Historial").performClick()

        composeTestRule.onNodeWithText("5+5 = 10.0").assertIsDisplayed()
    }

    @Test
    fun test11_limpiarHistorial_vaciaLista() {
        composeTestRule.onNodeWithText("2").performClick()
        composeTestRule.onNodeWithText("+").performClick()
        composeTestRule.onNodeWithText("2").performClick()
        composeTestRule.onNodeWithText("=").performClick()

        composeTestRule.onNodeWithText("Historial").performClick()
        composeTestRule.onNodeWithText("Borrar").performClick()

        composeTestRule.onNodeWithText("Todavía no hay operaciones").assertIsDisplayed()
    }

    @Test
    fun test12_botonVolverACalcular_regresaACalculadora() {
        composeTestRule.onNodeWithText("Historial").performClick()
        composeTestRule.onNodeWithText("Volver a calcular").performClick()

        composeTestRule.onNodeWithText("AC").assertIsDisplayed()
    }
}
