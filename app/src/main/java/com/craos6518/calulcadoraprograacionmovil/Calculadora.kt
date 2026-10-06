package com.craos6518.calulcadoraprograacionmovil

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val botoneslista = listOf(
    "C", "AC", "%", "/",
    "7", "8", "9", "X",
    "4","5", "6", "-",
    "1", "2","3", "+",
    "+/-", "0", ",", "="
)
@Composable
fun Calculadora(modifier: Modifier = Modifier, viewModel: ModeloVistaCalculadora) {

    val ecuacionText by viewModel.ecuacionText.observeAsState("")
    val resultadosText by viewModel.resultadosText.observeAsState("0")

    Box(modifier = modifier) {
        Column (
            modifier= modifier.fillMaxSize(),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = ecuacionText,  // vista de ecuacion (OPERACIÓN)
                style = TextStyle(
                    fontSize = 30.sp,
                    textAlign = TextAlign.End,
                ),
                maxLines = 5,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = resultadosText, // Vista del resultado
                style = TextStyle(
                    fontSize = 60.sp,
                    textAlign = TextAlign.End,
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(10.dp))
            LazyVerticalGrid(columns = GridCells.Fixed(4)
            ) {
                items(botoneslista){
                    CalculadoraBoton(btn = it, clickBoton = {
                        viewModel.ClickBoton(it)
                    })
                }
            }
        }
    }
}

@Composable
fun CalculadoraBoton(btn : String, clickBoton : ()-> Unit) {
    Box(modifier = Modifier.padding(10.dp)){
        FloatingActionButton(
            onClick = clickBoton,
            modifier = Modifier.size(80.dp),
            shape = CircleShape,
            containerColor = obtenerColor(btn),
            contentColor =  Color.White
        ) {
            Text(text = btn, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }
}
fun obtenerColor(btn : String): Color {
    if (btn == "C" || btn == "AC" || btn == "%")
        return Color(0xFF5D5D5D)
    if (btn == "/" || btn == "X" || btn == "-" || btn == "+" || btn == "=")
        return Color(0xFFFF9100)
    return Color(0xFF343434)

}